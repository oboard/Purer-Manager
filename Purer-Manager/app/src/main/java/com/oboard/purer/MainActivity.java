package com.oboard.purer;

import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.view.Menu;

public class MainActivity extends Activity {

    ArgbEvaluator ae = new ArgbEvaluator();
    Switch main_launch;
    ImageView main_image;
    TextView main_title;
    ListView main_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setElevation(0);
        setContentView(R.layout.main);

        S.init(this, "com.oboard.purer");

        main_title = (TextView)findViewById(R.id.main_title);
        main_image = (ImageView)findViewById(R.id.main_image);
        main_list = (ListView)findViewById(R.id.main_list);
        main_list.setAdapter(new MyAdapter(this, getDate()));
        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> av, View v, int p, long l) {
                    String mm = getDate().get(p).get("pack").toString();
                    if (!mm.equals(getPackageName())) PurerService.openApp(MainActivity.this, mm);
                }
            });
        main_launch = (Switch)findViewById(R.id.main_launch);
        main_launch.setChecked(S.get("s", false));
        if (S.get("s", false)) {
            main_title.setText("Purer 框架已启动");
            main_title.setTextColor(getColor(R.color.pgreen));
            main_image.setImageResource(R.drawable.ic_check_circle);
            main_image.setBackgroundColor(main_title.getCurrentTextColor());
        }
        main_launch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton button, boolean b) {
                    ValueAnimator mAni;
                    if (b) {
                        mAni = ValueAnimator.ofFloat(0, 1);
                        main_title.setText("Purer 框架已启动");
                        main_image.setImageResource(R.drawable.ic_check_circle);
                    } else {
                        mAni = ValueAnimator.ofFloat(1, 0);
                        main_title.setText("Purer 框架未启动");
                        main_image.setImageResource(R.drawable.ic_error);
                    }

                    S.put("s", b);
                    S.ok();

                    //main_list.setAdapter(new MyAdapter(MainActivity.this, getDate()));
                    mAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float av = animation.getAnimatedValue();
                                main_title.setTextColor(ae.evaluate(av, getColor(R.color.pred), getColor(R.color.pgreen)));
                                main_image.setBackgroundColor(main_title.getCurrentTextColor());
                            }
                        });
                    mAni.setDuration(300).start();
                }
            });
    }
    
    
    
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.FIRST, 0, 0, "设置").setIcon(R.drawable.ic_settings).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
    
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("设置")) {
            startActivity(new Intent(this, SettingsDiglog.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //添加一个得到数据的方法，方便使用
    private List<HashMap<String, Object>> getDate() {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        //为动态数组添加数据 
        final PackageManager packageManager = getApplication().getPackageManager();
        // get all apps
        final List<PackageInfo> app = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (int i = 0; i < app.size(); i++) {
            //如果是Purer模块则加入列表
            try {
                String pu = packageManager.getApplicationInfo(app.get(i).packageName, PackageManager.GET_META_DATA).metaData.getString("purermodule", "");
                if (pu != null) {
                    if (pu.equals("on")) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("pack", app.get(i).packageName);
                        map.put("title", app.get(i).applicationInfo.loadLabel(packageManager));
                        map.put("info", packageManager.getApplicationInfo(app.get(i).packageName, PackageManager.GET_META_DATA).metaData.getString("purerdescription"));
                        map.put("img", app.get(i).applicationInfo.loadIcon(packageManager));
                        data.add(map);
                    }
                }
            } catch (Exception e) {

            }
        }
        return data;
    }

    //ViewHolder静态类
    static class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView info;
    }
    class MyAdapter extends BaseAdapter {   
        private LayoutInflater mInflater = null;
        private List<HashMap<String, Object>> data;

        private MyAdapter(Context context, List<HashMap<String, Object>> l) {
            this.mInflater = LayoutInflater.from(context);
            data = l;
        }
        @Override
        public int getCount() {
            //在此适配器中所代表的数据集中的条目数
            return data.size();
        }
        @Override
        public Object getItem(int position) {
            //获取数据集中与指定索引对应的数据项
            return data.get(position);
        }
        @Override
        public long getItemId(int position) {
            //获取在列表中与指定索引对应的行id
            return position;
        }

        //获取一个在数据集中指定索引的视图来显示数据
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if (convertView == null) {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.item, null);
                holder.img = (ImageView)convertView.findViewById(R.id.item_icon);
                holder.title = (TextView)convertView.findViewById(R.id.item_text1);
                holder.info = (TextView)convertView.findViewById(R.id.item_text2);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.img.setImageDrawable((Drawable)data.get(position).get("img"));
            holder.title.setText((String)data.get(position).get("title"));
            holder.info.setText((String)data.get(position).get("info"));

            return convertView;
        }

    }

    class SpringInterpolator implements TimeInterpolator {
        private static final float DEFAULT_FACTOR = 0.4f;

        private float mFactor;

        public SpringInterpolator() {
            this(DEFAULT_FACTOR);
        }

        public SpringInterpolator(float factor) {
            mFactor = factor;
        }

        @Override
        public float getInterpolation(float input) {
            // pow(2, -10 * input) * sin((input - factor / 4) * (2 * PI) / factor) + 1
            return (float) (Math.pow(2, -10 * input) * Math.sin((input - mFactor / 4.0d) * (2.0d * Math.PI) / mFactor) + 1);

        }
    }
}
