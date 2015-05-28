package com.wlp.count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;




public class MemoryActivity extends Activity {
	int position;
	String date;
	String content;
	Dao file;
	List<Map<String, String>> ListItems;
	Button moreButton;
	SimpleAdapter simpleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	setContentView(R.layout.memory);

		// 获得按钮
		moreButton = (Button) findViewById(R.id.moreButton);

		file = new Dao();
		// 暂时给一个数据
		// file.WriteDataInMemory(this,"/sdcard/memoryData.txt", "xxxx.xx.xx",
		// "test");
		ArrayList<Map<String, String>> array = file.getDate(
				"/sdcard/memoryData.txt");
		// 创建一个list集合，元素为map
		ListItems = new ArrayList<Map<String, String>>();

		for (int i = 0; i < array.size(); i++) {
			Map<String, String> tempListItems = new HashMap<String, String>();
			tempListItems.put("date", array.get(i).get("date"));
			tempListItems.put("content", array.get(i).get("content"));
			ListItems.add(tempListItems);
		}
		Map<String, String> tempListItems = new HashMap<String, String>();
		tempListItems.put("date", "1.2");
		tempListItems.put("content","hehe");
		ListItems.add(tempListItems);

		// 创建simpleAdapter
		simpleAdapter = new SimpleAdapter(this, ListItems,
				R.layout.memory_item, new String[] { "date", "content" },
				new int[] { R.id.date, R.id.content });

		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(simpleAdapter);
//		//点击
//		list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				Map<String, String> item=ListItems.get(position);
//				
//				Intent intent=new Intent(MemoryActivity.this,AlarmActivity.class);
//				intent.putExtra("date", item.get("date"));
//				startActivity(intent);
//			}
//		});
		
		
		//长按
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
			int position, long id) {
			MemoryActivity.this.position=position;
			DeleteDialog();
			return false;
			}
			});

		moreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				intent.setClass(MemoryActivity.this, MemoryMoreActivity.class);

				startActivityForResult(intent, 1000);

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1000 && resultCode == 1001) {
			ListItems.clear();
			ArrayList<Map<String, String>> array=file.getDate("/sdcard/memoryData.txt");
			for (int i = 0; i < array.size(); i++) {
				Map<String, String> tempListItems = new HashMap<String, String>();
				tempListItems.put("date", array.get(i).get("date"));
				tempListItems.put("content", array.get(i).get("content"));
				ListItems.add(tempListItems);
			}
			simpleAdapter.notifyDataSetChanged();
		
		}
	}

	
	
	//删除
	private void DeleteDialog() {
		AlertDialog.Builder builder = new Builder(MemoryActivity.this);

		builder.setMessage("确定删除文件？");
		builder.setTitle("提示");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
		//这里File构造方法参数就是从你list读取的文件路径
		
		//通知adapter 更新
		ListItems.remove(position);
		file.Delete("/sdcard/memoryData.txt", ListItems);
		simpleAdapter.notifyDataSetChanged();
		}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

		}
		});
		builder.create().show();
		}
}
