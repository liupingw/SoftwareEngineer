package com.wlp.count;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Dao {

	FileOutputStream osw = null;

	// 写数据到文件
	public int WriteData(String filename, String date, String content) {
		ArrayList<Map<String, String>> array = getDate(filename);
		File f = new File(filename);
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			osw = new FileOutputStream(filename, true);
			osw.write((date + "+" + content + "\r\n").getBytes());
			for (int i = 0; i < array.size(); i++) {
				osw.write((array.get(i).get("date") + "+"
						+ array.get(i).get("content") + "\r\n").getBytes());
			}
			osw.close();
			// os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("找不到文件memory在写数据时");
			e.printStackTrace();
		}

		return 1;
	}

	// 写数据到文件
	public int WriteData(String filename, String date, String name,
			String content) {
		ArrayList<Map<String, String>> array = getTimeMachine(filename);
		File f = new File(filename);
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			osw = new FileOutputStream(filename, true);
			osw.write((date + "+" + name + "+" + content + "\r\n").getBytes());
			for (int i = 0; i < array.size(); i++) {
				osw.write((array.get(i).get("date") + "+"
						+ array.get(i).get("name") + "+"
						+ array.get(i).get("content") + "\r\n").getBytes());
			}
			osw.close();
			// os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("找不到文件memory在写数据时");
			e.printStackTrace();
		}

		return 1;
	}

	// 从文件 获取数据
	public ArrayList<Map<String, String>> getDate(String filename) {
		ArrayList<Map<String, String>> array = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;

		try {
			FileInputStream fin = new FileInputStream(filename);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fin));
			// 逐行读取
			String line = reader.readLine();
			System.out.println("第一行" + line);
			while (line != null) {

				System.out.println("循环内部" + line);
				String pattern = "\\+";
				Pattern pat = Pattern.compile(pattern);
				String[] temp = pat.split(line);
				if (temp.length >= 2) {
					System.out.println("切割后的" + temp[0] + "    " + temp[1]);
					map = new HashMap<String, String>();
					map.put("date", temp[0]);
					map.put("content", temp[1]);
					array.add(map);
				}
				line = reader.readLine();
			}
			reader.close();
			fin.close();

		} catch (FileNotFoundException e) {
			System.out.println("NOt Found");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
	}

	// 从time文件 获取数据
	public ArrayList<Map<String, String>> getTimeMachine(String filename) {
		ArrayList<Map<String, String>> array = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;

		try {
			FileInputStream fin = new FileInputStream(filename);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fin));
			// 逐行读取
			String line = reader.readLine();
			System.out.println("第一行" + line);
			while (line != null) {

				System.out.println("循环内部" + line);
				String pattern = "\\+";
				Pattern pat = Pattern.compile(pattern);
				String[] temp = pat.split(line);
				if (temp.length >= 3) {
					System.out.println("切割后的" + temp[0] + "    " + temp[1]);
					map = new HashMap<String, String>();
					map.put("date", temp[0]);
					map.put("name", temp[1]);
					map.put("content", temp[2]);
					array.add(map);
				}
				line = reader.readLine();
			}
			reader.close();
			fin.close();

		} catch (FileNotFoundException e) {
			System.out.println("NOt Found");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
	}

	public void Delete(String filename, List<Map<String, String>> array) {

		File f = new File(filename);
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (array.size() > 0){
			if (array.get(0).size() > 2) {
				for (int i = array.size() - 1; i >= 0; i--) {
					WriteData(filename, array.get(i).get("date"), array
							.get(i).get("name"), array.get(i).get("content"));
				}

			} else {
				for (int i = array.size() - 1; i >= 0; i--) {
					WriteData(filename, array.get(i).get("date"), array
							.get(i).get("content"));
				}
			}
		}
	}

}
