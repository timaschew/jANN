package de.unikassel.ann.strategy;

import de.unikassel.ann.config.NetConfig;

public abstract class Strategy {

	protected boolean stopTraining = false;

	protected NetConfig config;

	public NetConfig getConfig() {
		return config;
	}

	public void setConfig(NetConfig config) {
		this.config = config;
	}

	public boolean stop() {
		return stopTraining;
	}

	abstract public void preIteration();

	abstract public void postIteration();

}
