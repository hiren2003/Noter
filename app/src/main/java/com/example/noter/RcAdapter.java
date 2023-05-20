package com.example.noter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class RcAdapter extends RecyclerView.Adapter<RcAdapter.ViewHolder> {
    Context context;
    ArrayList<ClsModel> arrayList;

    public RcAdapter(Context context, ArrayList<ClsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RcAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lytraw,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RcAdapter.ViewHolder holder, int position) {
           holder.title.setText(arrayList.get(holder.getAdapterPosition()).Title);
           holder.description.setText(arrayList.get(holder.getAdapterPosition()).Description);
        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.setAnimation(anim);
           holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   Dialog dg=new Dialog(context);
                   dg.setContentView(R.layout.delete);
                   dg.getWindow().setBackgroundDrawableResource(R.drawable.crv);
                   Button yes=dg.findViewById(R.id.yes);
                   Button no=dg.findViewById(R.id.no);
                   no.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dg.dismiss();
                       }
                   });
                   yes.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                         DB_HELPER db_helper=new DB_HELPER(context);
                         db_helper.del(arrayList.get(position).Id);
                         notifyItemRemoved(position);
                         arrayList.remove(position);
                         dg.dismiss();
                         Toast.makeText(context, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                       }
                   });
                   dg.show();
                   return false;
               }
           });
           holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Dialog dialog = new Dialog(context);
                   dialog.setContentView(R.layout.lytdg);
                   dialog.getWindow().setBackgroundDrawable(getDrawable(context,R.drawable.crv));
                   dialog.show();
                   Button btn = dialog.findViewById(R.id.button);
                   EditText edttitle = dialog.findViewById(R.id.editTextText);
                   EditText edtdes = dialog.findViewById(R.id.editTextText2);
                   edttitle.setText(arrayList.get(holder.getAdapterPosition()).Title);
                   edttitle.setSelection(edttitle.getText().length());
                   edtdes.setText(arrayList.get(holder.getAdapterPosition()).Description);
                   btn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                         DB_HELPER db_helper =new DB_HELPER(context);
                         ClsModel clsModel=new ClsModel(arrayList.get(holder.getAdapterPosition()).Id,edttitle.getText().toString(),edtdes.getText().toString());
                         db_helper.update(clsModel);
                         arrayList.set(holder.getAdapterPosition(),new ClsModel(arrayList.get(holder.getAdapterPosition()).Id,edttitle.getText().toString(),edtdes.getText().toString()));
                         dialog.dismiss();
                         notifyItemRemoved(holder.getAdapterPosition());
                           Toast.makeText(context, "Item Updated", Toast.LENGTH_SHORT).show();
                       }

                   });
               }
           });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView title,description;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.txttitle);
            description=itemView.findViewById(R.id.txtdes);
            relativeLayout =itemView.findViewById(R.id.rl);
        }
    }

}
