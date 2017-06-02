package com.cao.utils;

import org.hibernate.dialect.MySQL5Dialect;

public class DefaultCharacterSet extends MySQL5Dialect {
	  @Override  
      public String getTableTypeString() {  
          return " ENGINE=InnoDB DEFAULT CHARSET=utf8";    
      }  
}
