package com.commonlib.util;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonViewHolder {
	public View convertView;

	private int position;
	
	public static CommonViewHolder getCommonViewHolder(View convertView,
			Context context, int layoutRes) {
		if (convertView != null) {
			return (CommonViewHolder) convertView.getTag();
		} else {
			convertView = View.inflate(context, layoutRes, null);
			return new CommonViewHolder(convertView);
		}

	}
	
	public CommonViewHolder(View convertView) {
		super();
		this.convertView = convertView;
		convertView.setTag(this);
	}

	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}


	SparseArray<View> views = new SparseArray<View>();

	// HashMap<Integer, View> views = new HashMap<Integer, View>();
	// 类型推导（根据参数推导）
	public <T extends View> T getView(int viewId, Class<T> klass) {
		return (T) getView(viewId);
	}
	
	public TextView getTv(int viewId){
		return  getView(viewId, TextView.class);
	}
	public ImageView getIv(int viewId){
		return  getView(viewId, ImageView.class);
	}

	// 类型推导（根据返回值推导）
	public <T extends View> T getView(int viewId) {
		if (views.get(viewId) == null) {
			Log.d("cvh", "findViewById");
			views.put(viewId, convertView.findViewById(viewId));
		}
		return (T) views.get(viewId);

	}

	// public View getView(int viewId){
	// if(views.get(viewId) == null){
	// Log.d("cvh", "findViewById");
	// views.put(viewId, convertView.findViewById(viewId));
	// }
	// return views.get(viewId);
	// }

	public static CommonViewHolder getCVH(View convertView, Context context,
			int layoutRes) {
		if (convertView == null) {
			convertView = View.inflate(context, layoutRes, null);
			return new CommonViewHolder(convertView);
		}
		return (CommonViewHolder) convertView.getTag();

	}
}
