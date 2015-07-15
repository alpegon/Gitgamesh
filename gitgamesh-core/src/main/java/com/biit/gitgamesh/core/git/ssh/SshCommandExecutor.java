package com.biit.gitgamesh.core.git.ssh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

	public void connect() throws JSchException {
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

	private void saveExitInDirectory(String text, String directory, String file) {
		File exitDirectory;
		byte b[] = text.getBytes();
		exitDirectory = new File(directory + File.separator + file);
		FileOutputStream exitChannel = null;
		try {
			exitChannel = new FileOutputStream(exitDirectory);
		} catch (FileNotFoundException ex) {
			GitgameshLogger.errorMessage(this.getClass().getName(), ex);
		}
		try {
			exitChannel.write(b);
			exitChannel.close();
		} catch (IOException ex) {
			GitgameshLogger.errorMessage(this.getClass().getName(), ex);
		}
	}

	public void runCommands(List<String> commands, String directory, String file) {
		String concatenatedCommands = "";
		for (String command : commands) {
			concatenatedCommands += command + "; ";
		}
		runCommand(concatenatedCommands, directory, file);
	}

	public void runCommand(String command, String directory, String file) {
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
						saveExitInDirectory((new String(byteBuffer, 0, i)), directory, file);
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
}