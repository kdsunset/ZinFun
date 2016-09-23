package com.myapp.zin.zinfun.factory;

import com.myapp.zin.zinfun.fragment.MyBaseFragement;
import com.myapp.zin.zinfun.fragment.QingChunMmFragment;
import com.myapp.zin.zinfun.fragment.RihanMmFragment;
import com.myapp.zin.zinfun.fragment.SexMmFragment;
import com.myapp.zin.zinfun.fragment.SiwaMmFragment;
import com.myapp.zin.zinfun.fragment.XiezhenMmFragment;

import java.util.HashMap;

public class FragmentFactory {
	private static HashMap<Integer, MyBaseFragement> hashMap = new HashMap<Integer, MyBaseFragement>();
	public static MyBaseFragement createFragment(int position) {
		MyBaseFragement baseFragment = null;
		baseFragment = hashMap.get(position);
		if(baseFragment !=null){
			//根据索引获取到了fragment对象,直接返回即可
			return baseFragment;
		}else{
			//在没有此索引指向fragment的时候创建逻辑
			switch (position) {
				case 0:
					baseFragment = new SexMmFragment();
					break;
				case 1:
					baseFragment = new RihanMmFragment();
					break;
				case 2:
					baseFragment = new SiwaMmFragment();
					break;
				case 3:
					baseFragment = new XiezhenMmFragment();
					break;
				case 4:
					baseFragment = new QingChunMmFragment();
					break;
			/*case 5:
				baseFragment = new CategoryFragment();
				break;
			case 6:
				baseFragment = new HotFragment();
				break;*/
			}
			hashMap.put(position, baseFragment);
			return baseFragment;
		}
	}
}
