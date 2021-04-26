package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.Nullable;
import bean.Store;

public class MyAdapterStore extends BaseAdapter {

    private ArrayList<Store> storeList;
    private final Activity activity;
    private Handler handler;

    public MyAdapterStore(ArrayList<Store> storeList, Activity activity, Handler handler) {
        this.storeList = storeList;
        this.activity = activity;
        this.handler = handler;
    }

    public ArrayList<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(ArrayList<Store> storeList){
        this.storeList = storeList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int position) {
        return storeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return storeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAdapterViewHolder myAdapterViewHolder = null;

        if (convertView == null) {
            myAdapterViewHolder = new MyAdapterViewHolder();

            convertView = activity.getLayoutInflater().inflate(R.layout.my_adapter_store, parent, false);

            //pegando as referências das Views
            myAdapterViewHolder.tvName    = convertView.findViewById(R.id.textViewStoreName);
            myAdapterViewHolder.tvName.setTypeface(null, Typeface.BOLD);
            myAdapterViewHolder.tvAddress = convertView.findViewById(R.id.textViewStoreAddress);
            myAdapterViewHolder.imageView = convertView.findViewById(R.id.imageView);


            convertView.setTag(myAdapterViewHolder);
        } else {
            myAdapterViewHolder = (MyAdapterViewHolder) convertView.getTag();
        }

        if (myAdapterViewHolder != null) {
            Store store = storeList.get(position);

            //populando as Views
            myAdapterViewHolder.tvName.setText(store.getName());
            //myAdapterViewHolder.tvAddress.setText(store.getAddress());
            try {
                System.out.println("ARQUIVO DE LOGO :"+store.getLogo());
               // Glide.with(activity).load(store.getLogo()).into(myAdapterViewHolder.imageView);
                //Glide.with(activity).load(R.drawable.ic_launcher).into(myAdapterViewHolder.imageView); OK funciona com icones da própria ferramenta - pasta drawable


               // Glide.with(activity).load(R.mipmap.ic_alemao ).into(myAdapterViewHolder.imageView);

//                Bitmap bitmap = BitmapFactory.decodeFile("D:/FoodDeliveryConcept/app/src/main/res/mipmap-hdpi/ic_alemao.png");
//                myAdapterViewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile("D:/FoodDeliveryConcept/app/src/main/res/mipmap-hdpi/ic_alemao.png"));

               // Glide.with(activity).load(new File("D://FoodDeliveryConcept/app/src/main/res/mipmap-hdpi/ic_alemao.png")).into(myAdapterViewHolder.imageView); nao carregou


//                Glide
//                        .with(activity)
//                        .asBitmap()
//                        .load("D:/FoodDeliveryConcept/app/src/main/res/mipmap-hdpi/ic_alemao.png")
//                        .into(myAdapterViewHolder.imageView);


//                MyAdapterViewHolder finalMyAdapterViewHolder = myAdapterViewHolder;
//                new Thread(){
//                    public void run() {
//                        Bitmap img = null;
//                        try {
//                            URL url = new URL("http://www.thiengo.com.br/img/system/logo/thiengo-80-80.png");
//                            HttpsURLConnection conexao = (HttpsURLConnection) url.openConnection();
//                            InputStream input = conexao.getInputStream();
//                            img = BitmapFactory.decodeStream(input);
//                            final Bitmap imgAux = img;
//                            handler.post(new Runnable(){
//                                public void run(){
//                                    finalMyAdapterViewHolder.imageView.setImageBitmap(imgAux);
//                                }
//                            });
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();



//                String val = "D://FoodDeliveryConcept/app/src/main/res/mipmap-hdpi/ic_alemao.png";

              String val = "https://www.thiengo.com.br/img/system/logo/thiengo-80-80.png";



//                Glide.with(this.activity)
//                        .load( new File(val))
//                        .into(myAdapterViewHolder.imageView);



               Glide.with(activity).load(R.mipmap.ic_launcher).into(myAdapterViewHolder.imageView);
//               Glide.with(activity).load(R.mipmap.ic_alemao).into(myAdapterViewHolder.imageView);


//               File file = new File("D://FoodDeliveryConcept/app/src/main/res/mipmap-hdpi/ic_alemao.png");
//               Glide.with(activity).load(file).into(myAdapterViewHolder.imageView);

//                Picasso.get().load("D:/FoodDeliveryConcept/app/src/main/res/mipmap-hdpi/ic_alemao.png").into(myAdapterViewHolder.imageView);


//                File file = new File(Environment.getExternalStorageDirectory(),"ic_alemao.png");
//                Glide.with(activity).load(file).into(myAdapterViewHolder.imageView);

                File file = new File("D://teste.txt");

                if ( file.exists() ){
                    Glide.with(activity).load(file).into(myAdapterViewHolder.imageView);
                }
                else{
                    System.out.println("Faiô"); //ic_alemao.png
                }






            }catch(Throwable t){
                myAdapterViewHolder.imageView.setImageResource(R.drawable.ic_launcher);
            }

        }

        return convertView;
    }

    public void setDataList(ArrayList<Store> stores) {
    }

    private class MyAdapterViewHolder {
        public TextView tvName;
        public TextView tvAddress;
        public TextView tvPrice;
        public ImageView imageView;
    }
}