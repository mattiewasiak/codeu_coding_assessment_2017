// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

final class MyJSON implements JSON {

  HashMap parsedData = new HashMap();
	
  @Override
  public JSON getObject(String name) {
	  
	
    return (JSON) parsedData.get(name);
  }

  @Override
  public JSON setObject(String name, JSON value) {
    parsedData.put(name, value);
    return this;
  }

  @Override
  public String getString(String name) {
   
    return (String)parsedData.get(name);
  }

  @Override
  public JSON setString(String name, String value) {
	parsedData.put(name, value);
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
	 
	  
	  Set<String> keys = parsedData.keySet();
	 
	  for ( String key : keys) {
		    
		    
		    if(parsedData.get(key) instanceof MyJSON){
		    	
		    	names.add(key);
		    }
		     
		}
	  
  }

  @Override
  public void getStrings(Collection<String> names) {
	  Set<String> keys = parsedData.keySet();
	  for ( String key : keys) {
		    if(parsedData.get(key) instanceof String){
		    	names.add(key);
		    }
		     
		}
  }
}
