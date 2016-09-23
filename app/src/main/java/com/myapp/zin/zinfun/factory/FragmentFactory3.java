package com.myapp.zin.zinfun.factory;

import com.myapp.zin.zinfun.fragment.BaseFragment3;
import com.myapp.zin.zinfun.fragment.QingchunmmFragment3;
import com.myapp.zin.zinfun.fragment.RiHanmmFragment3;
import com.myapp.zin.zinfun.fragment.SiwaFragment3;
import com.myapp.zin.zinfun.fragment.XiezhenmmFragment3;

import java.util.HashMap;

public class FragmentFactory3 {
	private static HashMap<Integer, BaseFragment3> hashMap = new HashMap<Integer, BaseFragment3>();
	public static BaseFragment3 createFragment(int position) {
		BaseFragment3 baseFragment = null;
		baseFragment = hashMap.get(position);
		if(baseFragment !=null){
			//根据索引获取到了fragment对象,直接返回即可
			return baseFragment;
		}else{
			//在没有此索引指向fragment的时候创建逻辑
			switch (position) {
			case 0:
				/*baseFragment = new SexMmFragment();*/
				break;
			case 1:
				baseFragment = new RiHanmmFragment3();
				break;
			case 2:
				baseFragment = new SiwaFragment3();
				break;
			case 3:
				baseFragment = new XiezhenmmFragment3();
				break;
			case 4:
				baseFragment = new QingchunmmFragment3();
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
