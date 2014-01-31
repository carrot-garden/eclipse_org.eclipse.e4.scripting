package org.eclipse.ease.service;

import org.eclipse.core.runtime.IConfigurationElement;

import com.google.common.base.Function;

public class ScriptType {

	public static class ToScriptType implements Function<ScriptType, String> {

		@Override
		public String apply(ScriptType arg0) {
			return arg0.getName();
		}

	}

	public static class ToExtensionFile implements Function<ScriptType, String> {

		@Override
		public String apply(ScriptType arg0) {
			return arg0.getDefaultExtension();
		}

	}

	// protected String scritpType;

	// protected String extension;

	private static final String NAME = "name";
	private static final String DEFAULT_EXTENSION = "defaultExtension";

	private final IConfigurationElement fConfigurationElement;

	public ScriptType(IConfigurationElement configurationElement) {
		fConfigurationElement = configurationElement;
	}

	// public static ScriptType createScriptType(String scriptType, String extension) {
	// ScriptType result = new ScriptType();
	// result.setExtension(extension);
	// result.setScritpType(scriptType);
	// return result;
	// }

	// /**
	// * @return the scritpType
	// */
	// public String getScritpType() {
	// return scritpType;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see java.lang.Object#hashCode()
	// */
	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = (prime * result) + ((extension == null) ? 0 : extension.hashCode());
	// result = (prime * result) + ((scritpType == null) ? 0 : scritpType.hashCode());
	// return result;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see java.lang.Object#equals(java.lang.Object)
	// */
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// ScriptType other = (ScriptType) obj;
	// if (extension == null) {
	// if (other.extension != null)
	// return false;
	// } else if (!extension.equals(other.extension))
	// return false;
	// if (scritpType == null) {
	// if (other.scritpType != null)
	// return false;
	// } else if (!scritpType.equals(other.scritpType))
	// return false;
	// return true;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see java.lang.Object#toString()
	// */
	// @Override
	// public String toString() {
	// return "ScriptType [scritpType=" + scritpType + ", extension=" + extension + "]";
	// }
	//
	// /**
	// * @param scritpType
	// * the scritpType to set
	// */
	// public void setScritpType(String scritpType) {
	// this.scritpType = scritpType;
	// }
	//
	// /**
	// * @return the extension
	// */
	// public String getExtension() {
	// return extension;
	// }
	//
	// /**
	// * @param extension
	// * the extension to set
	// */
	// public void setExtension(String extension) {
	// this.extension = extension;
	// }

	public String getName() {
		return fConfigurationElement.getAttribute(NAME);
	}

	public String getDefaultExtension() {
		return fConfigurationElement.getAttribute(DEFAULT_EXTENSION);
	}
}
