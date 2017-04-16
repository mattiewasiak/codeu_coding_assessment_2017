package com.google.codeu.codingchallenge;

import java.io.IOException;

final class MyJSONParser implements JSONParser{
	
	

	public int skipSpaces(String in, int startingIndex) throws IOException{
		//returns the index of the next character not a space
		
		while(in.charAt(startingIndex) == ' '){
			//ensure the end of the string hasn't been reached
			if(startingIndex+1 > in.length()-1){
				throw new IOException();
			}
			
			startingIndex++;
			
		} 

		return startingIndex;
	}

	

	public int getStringEnd(String in, int currentChar) throws IOException{
		
		while(in.charAt(currentChar) != '"'){
			
			if(currentChar >= in.length()-1){
				
				throw new IOException();
			}

			if(in.charAt(currentChar) == '\\'){
				if(currentChar  < in.length()){
					
					if(in.charAt(currentChar+1) == '\\' || in.charAt(currentChar+1) == 't' || in.charAt(currentChar+1) == 'n' || in.charAt(currentChar+1) == '"' ){
						
						currentChar++;
					}
					
					else{
						
						throw new IOException();
					}
				}
				
			}
			currentChar++;
			


		}

		return currentChar;
	}

	@Override
	public JSON parse(String in) throws IOException{

		//should be returned
		MyJSON parsed = new MyJSON();

		
		

		//return occurs when a '}' is reached

		//know the start is {
		int currentChar = 0;
		
		currentChar = skipSpaces(in, 0);
		
		if(in.charAt(currentChar) == '{'){
			currentChar = skipSpaces(in, currentChar+1);
			
			
		}
		else{
			throw new IOException();
		}
		
		
		
		
		String lastChar = "{";
		String key = "";
		while(in.charAt(currentChar) != '}'){
			
			
			
			currentChar = skipSpaces(in, currentChar);
			
			//if lastChar is {, the next char has to be "
			if(lastChar == "{" || lastChar == ","){
				if(in.charAt(currentChar) != '"'){
					
					throw new IOException();
				}
				
				else{
					currentChar++;
					//gather the key
					int keyEnd = getStringEnd(in, currentChar);
					
					key = in.substring(currentChar, keyEnd);
					
					currentChar = keyEnd + 1;
					
					lastChar = "keyquote";
					currentChar = skipSpaces(in, currentChar);
					
				}
				
			}

			//if lastChar is keyquote, new char must be :
			if(lastChar == "keyquote"){
				if(in.charAt(currentChar) != ':'){
					throw new IOException();
				}
				else{
					lastChar = ":";
					currentChar = skipSpaces(in, currentChar+1);

				}
			}

			//after a colon should either be an object or a string
			if(lastChar == ":"){
				
				
				currentChar = skipSpaces(in, currentChar);
				
				
				//string
				if(in.charAt(currentChar) == '"'){
					
					int valueEnd = getStringEnd(in, currentChar+1);
					
					String value = in.substring(currentChar+1, valueEnd);
					
					
					parsed.setString(key, value);
					currentChar = valueEnd + 1;
					lastChar = "valuequote";
					currentChar = skipSpaces(in, currentChar);
					
					
				}

				//object
				else if(in.charAt(currentChar) == '{'){
					
					
					int foundEnd = currentChar;
					while(in.charAt(foundEnd) != '}'){
						if(foundEnd >= in.length()){
							throw new IOException();
						}
						foundEnd++;
					}
					
					parsed.setObject(key, parse(in.substring(currentChar, foundEnd+1)));
					
					if(foundEnd < in.length()-2){
						currentChar = skipSpaces(in, foundEnd + 1);
					}
					else{
						currentChar = foundEnd;
					}
					lastChar = "valuequote";
					
				}
				else{
					throw new IOException();
				}
			}

			//if lastChar is valuequote, next char must be , (or }, but this won't go in the loop)
			if(lastChar == "valuequote"){
				
				if(in.charAt(currentChar) != ',' && in.charAt(currentChar) != '}'){
					
					throw new IOException();

				}
				else if(in.charAt(currentChar) == '}'){
					return parsed;
				}

				else{
					lastChar = ",";
					
					if(currentChar < in.length() - 1){
						
						currentChar = skipSpaces(in, currentChar+1);
					}
					
					

				}



			}
			
			if(currentChar >= in.length()-1){
				throw new IOException();
			}
			






			

		}
		
		return parsed;


	}



}