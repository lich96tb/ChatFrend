package com.example.lich96tb.chatfriend;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lich96tb on 8/2/2017.
 */

public class FragmentDocBao extends Fragment {
    ArrayList<News> ds;
    ListView lvDocBao;
    NewsAdapter adapter;
    private String vnexpr;
    String kenh;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.docbao, container, false);
        lvDocBao = view1.findViewById(R.id.lvDocBao);
        ds = new ArrayList<>();
        vnexpr = "https://vnexpress.net/rss/thoi-su.rss";
        kenh = "Thời sự";
        new loadRSS().execute(vnexpr);

        lvDocBao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                android.app.FragmentManager fragmentManager=getActivity().getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Fragmen_doc_bao fragmen_doc_bao=new Fragmen_doc_bao();
                Bundle bundle=new Bundle();
                bundle.putString("link",ds.get(i).getLink());
                fragmen_doc_bao.setArguments(bundle);
                fragmentTransaction.add(R.id.bbb,fragmen_doc_bao,"docbao");
                fragmentTransaction.commit();
            }
        });

        return view1;
    }

    ///////////////////
    class loadRSS extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... strings) {
            String url = vnexpr;
            //load data
            try {
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("item");
                for (Element item : elements) {
                    String title = item.select("title").text();
                    String link = item.select("link").text();
                    String tg = item.select("pubDate").text();
                    String description = item.select("description").text();
                    //des laf html => laij lam lai buoc tren
                    Document docimg = Jsoup.parse(description);
                    String imageURL = docimg.select("img").get(0).attr("src");
                    ds.add(new News(title, link, imageURL, tg, kenh));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<News> newses) {
            adapter = new NewsAdapter(getActivity(), R.layout.item2, ds);
            lvDocBao.setAdapter(adapter);
        }
    }
    //////////////////////


    }

