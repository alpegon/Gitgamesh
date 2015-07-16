package com.biit.gitgamesh.core.git.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

import com.biit.gitgamesh.logger.GitgameshLogger;
import com.biit.gitgamesh.utils.FileReader;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * This class generates the standard methods to connect by SSH.<br>
 * Based on the JCraft tools.
 */

public class SshCommandExecutor {

	private Channel channel;
	private Session session;
	private boolean commandOutputEnabled = false;
	private String sshUser, sshKeyFile, sshIp;
	private int sshPort = 22;

	public SshCommandExecutor(String sshUser, String sshKeyFile, String sshIp, int sshPort) {
		this.sshUser = sshUser;
		this.sshKeyFile = sshKeyFile;
		this.sshIp = sshIp;
		this.sshPort = sshPort;
	}

	public boolean isCommandOutputEnabled() {
		return commandOutputEnabled;
	}

	public void setCommandOutputEnabled(boolean print) {
		this.commandOutputEnabled = print;
	}

	public void connectSession() throws JSchException {
		try {
			JSch jsch = new JSch();
			// Private key must be byte array
			final byte[] prvkey = readMyPrivateKeyFromFile();
			// Empty passphrase for now, get real passphrase from MyUserInfo
			final byte[] emptyPassPhrase = new byte[0];

			jsch.addIdentity(sshUser, // String userName
					prvkey, // byte[] privateKey
					null, // byte[] publicKey
					emptyPassPhrase // byte[] passPhrase
			);

			session = jsch.getSession(sshUser, sshIp, sshPort);
			// We are not using password authentication
			session.setUserInfo(new JschUserInfo(""));
			session.connect();
		} catch (JSchException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e.getMessage() + " in connection to " + sshIp);
			throw e;
		}
	}

	public String runCommands(List<String> commands) {
		String concatenatedCommands = "";
		for (String command : commands) {
			concatenatedCommands += command + "; ";
		}
		GitgameshLogger.debug(this.getClass().getName(), "Executing commands: " + concatenatedCommands);
		return runCommand(concatenatedCommands);
	}

	/**
	 * Run the command passed as String.<br>
	 * 
	 * @param command
	 * @return The command output
	 */
	public String runCommand(String command) {

		GitgameshLogger.debug(this.getClass().getName(), "Executing command: " + command);

		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.setXForwarding(true);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] byteBuffer = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(byteBuffer, 0, 1024);
					if (i < 0) {
						break;
					}
					if (isCommandOutputEnabled()) {
						String output = new String(byteBuffer, 0, i);
						GitgameshLogger.debug(this.getClass().getName(), "Command return: " + output);
						return output;
					}
				}
				if (channel.isClosed()) {
					GitgameshLogger.debug(this.getClass().getName(), "exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(10);
				} catch (Exception ee) {
					GitgameshLogger.errorMessage(this.getClass().getName(), "Error while putting thread to sleep :"
							+ ee);
				}
			}
		} catch (Exception e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), "Error while running command :" + e);
		}
		return null;
	}

	public void disconnect() {
		try {
			channel.disconnect();
		} catch (NullPointerException npe) {
			GitgameshLogger.errorMessage(this.getClass().getName(), "Channel already disconnected:" + npe);
		}
		try {
			session.disconnect();
		} catch (NullPointerException npe) {
			GitgameshLogger.errorMessage(this.getClass().getName(), "Session already disconnected:" + npe);
		}
	}

	private byte[] readMyPrivateKeyFromFile() {
		try {
			return FileReader.getResource(sshKeyFile, StandardCharsets.UTF_8).getBytes();
		} catch (FileNotFoundException e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
		return null;
	}

	/**
	 * Returns a file from the remote machine.<br>
	 * Code based on http://www.jcraft.com/jsch/examples/ScpFrom.java.html.
	 * 
	 * @param filePath
	 * @return
	 */
	public byte[] getRemoteFile(String filePath) {

		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		Random rand = new Random();
		int randomFileName = rand.nextInt(99999999);
		Path tmpFilePath = FileSystems.getDefault().getPath(
				tempDir.getPath() + File.separator + randomFileName + ".stl");

		try {
			connectSession();
			// exec 'scp -f rfile' remotely
			String command = "scp -f " + filePath;

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] buf = new byte[1024];

			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();

			while (true) {
				int c = checkAck(in);
				if (c != 'C') {
					break;
				}

				// read '0644 '
				in.read(buf, 0, 5);

				long filesize = 0L;
				while (true) {
					if (in.read(buf, 0, 1) < 0) {
						// error
						break;
					}
					if (buf[0] == ' ')
						break;
					filesize = filesize * 10L + (long) (buf[0] - '0');
				}

				for (int i = 0;; i++) {
					in.read(buf, i, 1);
					if (buf[i] == (byte) 0x0a) {
						String file = new String(buf, 0, i);
						break;
					}
				}

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();

				FileOutputStream fos = new FileOutputStream(tmpFilePath.toString());
				int foo;
				while (true) {
					if (buf.length < filesize)
						foo = buf.length;
					else
						foo = (int) filesize;
					foo = in.read(buf, 0, foo);
					if (foo < 0) {
						// error
						break;
					}
					fos.write(buf, 0, foo);
					filesize -= foo;
					if (filesize == 0L)
						break;
				}

				fos.close();
				fos = null;

				if (checkAck(in) != 0) {
					System.exit(0);
				}

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();

			}
			return Files.readAllBytes(tmpFilePath);
		} catch (Exception e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
		}
		return null;
	}

	static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}

	/**
	 * Uploads a file to the remote machine.<br>
	 * Code based on http://www.jcraft.com/jsch/examples/ScpTo.java.html
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public void setRemoteFile(String filePath, File file, String fileName) throws IOException {
		FileInputStream fis = null;
		try {
			connectSession();
			// exec 'scp -t rfile' remotely
			String command = "scp -t " + filePath;
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();

			if (checkAck(in) != 0) {
				System.exit(0);
			}

			// send "C0644 filesize filename", where filename should not include
			// '/'
			long filesize = file.length();
			command = "C0644 " + filesize + " ";
			command += fileName + "\n";
			out.write(command.getBytes());
			out.flush();
			if (checkAck(in) != 0) {
				System.exit(0);
			}

			// send a content of the file
			fis = new FileInputStream(file);
			byte[] buf = new byte[1024];
			while (true) {
				int len = fis.read(buf, 0, buf.length);
				if (len <= 0)
					break;
				out.write(buf, 0, len);
			}
			fis.close();
			fis = null;
			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();
			if (checkAck(in) != 0) {
				System.exit(0);
			}
			out.close();
		} catch (Exception e) {
			GitgameshLogger.errorMessage(this.getClass().getName(), e);
			try {
				if (fis != null)
					fis.close();
			} catch (Exception ee) {
			}
		}
	}
}