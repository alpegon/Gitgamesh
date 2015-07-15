package com.biit.gitgamesh.core.git.ssh;

import com.jcraft.jsch.UserInfo;

public class JschUserInfo implements UserInfo {
	String password;

	public JschUserInfo(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean promptYesNo(String str) {
		return true;
	}

	@Override
	public String getPassphrase() {
		return null;
	}

	@Override
	public boolean promptPassphrase(String message) {
		return true;
	}

	@Override
	public boolean promptPassword(String message) {
		return true;
	}

	@Override
	public void showMessage(String message) {
	}
}