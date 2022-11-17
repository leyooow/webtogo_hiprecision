package com.ivant.cms.beans;

public abstract class DisplayProcessor {

	private static final int ZERO = 0;
	
	private static final String NEW_LINE = "\n";
	
	private static final String SPACE = " ";
	
	protected String formatDisplayText(String text, int length) {
		StringBuffer newText = new StringBuffer();
		StringBuffer buffer = new StringBuffer();
		int charCounter = ZERO;
		for(int index = ZERO;index < text.length();index++) {
			StringBuffer tempBuff = new StringBuffer();
			StringBuffer character = new StringBuffer(text.substring(index, index + 1));
			if(character.toString().equals(NEW_LINE)){
				charCounter = ZERO;
			} else {
				charCounter++;
					if(charCounter == length) {
						charCounter = ZERO;
							try {
								if(!text.substring(index + 1, index + 2).equals(SPACE)) {
									String bufferCopy = buffer.toString();
									for(int innerIndex = bufferCopy.length() - 1;innerIndex > 0;innerIndex--) {
										StringBuffer innerCharater = new StringBuffer(bufferCopy.substring(innerIndex, innerIndex + 1));
										if(innerCharater.toString().equals(SPACE)) {
											buffer.replace(0, buffer.length(), bufferCopy.toString().substring(0, innerIndex));
											tempBuff.append(bufferCopy.toString().substring(innerIndex + 1, bufferCopy.length()))
													.append(character);
											character = new StringBuffer(NEW_LINE);
											break;
										}
									}
								}
							} catch(IndexOutOfBoundsException a){}
					}
			}
			
			buffer.append(character);
			
			if(charCounter == ZERO) {
				newText.append(buffer);
					buffer = new StringBuffer();
					if(tempBuff.length() > ZERO) {
						buffer = new StringBuffer(tempBuff);
						charCounter += buffer.length();
					}
			}
			
			if(charCounter != ZERO && index == text.length() - 1 && buffer.length() > ZERO) {
				newText.append(buffer);
			}
			
		}
		
		return newText.toString();
	}
	
	protected int countLines(String text) {
		int count = 1;
		for(int index = 0; index < text.length();index++) {
			StringBuffer buff = new StringBuffer(text.substring(index, index + 1));
			if(buff.toString().equals("\n")) {
				count++;
			}
		}
		return count;
	}
}
