package com.letsservice.calllog.features.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.letsservice.calllog.R;
import com.letsservice.calllog.data.model.response.Call;


public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.PokemonViewHolder> {

    private List<Call> callList;

    @Inject
    CallLogAdapter() {

        callList = Collections.emptyList();
    }

    public void setPokemon(List<Call> callList) {
        this.callList = callList;
        notifyDataSetChanged();
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        String number = this.callList.get(position).getNumber();
        String name= this.callList.get(position).getName();
        String date= this.callList.get(position).getCallDate();
        String type= this.callList.get(position).getType();

        holder.onBind(name,number,date,type);
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }



    class PokemonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView nameText;
        @BindView(R.id.text_number)
        TextView nameNumber;
        @BindView(R.id.text_date)
        TextView nameDate;
        @BindView(R.id.text_type)
        TextView nameType;
        @BindView(R.id.ll_call)
        LinearLayout callEntry;
        @BindView(R.id.btn_call)
        Button call;
        @BindView(R.id.btn_show)
        Button show;
        PokemonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            call.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View view) {

                  String number =callList.get(getAdapterPosition()).getNumber();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" +number));
                   view.getContext().startActivity(intent);
                }
            });
            show.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View view) {

                    String number =callList.get(getAdapterPosition()).getNumber();
                    Intent intent= new Intent(view.getContext(),RecordingListActivity.class);
                    intent.putExtra("number",number);
                    view.getContext().startActivity(intent);
                }
            });
        }

        void onBind(String name,String number,String date,String type) {

            nameText.setText((name!=null)? name : number);
            nameNumber.setText(number);
            nameDate.setText(date);
            nameType.setText(type);

        }
    }
}
