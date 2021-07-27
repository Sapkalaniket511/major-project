package com.netspam.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Demo {

	public ArrayList<String> dupStr(String str, String arr[]){
		ArrayList<String> strList = new ArrayList<String>();
		
		for(int i=0;i<arr.length-5;i++){
			int count=0;
			for(int j=i;j<i+5;j++){
				if(str.equals(arr[j])){
					count++;
				}else{
					break;
				}
				if(count==5){
					strList.add(str);
					i=j;
					break;
				}
			}
			
			
		}
		
		return strList;
	}
	public static void main(String args[]){
		String arr[]={"aa","aa","aa","aa","aa","cc","aa","aa","aa","aa","aa","cc","aa","aa","bb","bb","bb","bb","bb","cc","bb","bb","bb","bb","bb","cc","cc","cc","cc","cc","cc","cc"};
		Demo demo=new Demo();
		Set<String> setList=new HashSet<String>(Arrays.asList(arr));
		ArrayList<String> setarList=new ArrayList<String>();
		setarList.addAll(setList);
		ArrayList<ArrayList<String>> arrList= new ArrayList<ArrayList<String>>();
		for(int i=0;i<setarList.size();i++){
			ArrayList<String> strList=demo.dupStr(setarList.get(i), arr);
			System.out.println("strList="+strList.size());
			if(strList.size()>0&&strList!=null)
			arrList.add(strList);
		}
		System.out.println("arrList="+arrList.size());
		
	}
}
