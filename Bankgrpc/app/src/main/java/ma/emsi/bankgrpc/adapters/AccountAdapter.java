package ma.emsi.bankgrpc.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.emsi.bankgrpc.R;
import ma.emsi.bankgrpc.stubs.Compte;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private List<Compte> accounts;

    public AccountAdapter(List<Compte> accounts) {
        this.accounts = accounts;
    }

    public void updateAccounts(List<Compte> newAccounts) {
        this.accounts = newAccounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Compte compte = accounts.get(position);
        holder.tvAccountId.setText("ID: " + compte.getId());
        holder.tvAccountSolde.setText("Solde: " + compte.getSolde());
        holder.tvAccountType.setText("Type: " + compte.getType());

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccountId, tvAccountSolde, tvAccountType;

        ViewHolder(View itemView) {
            super(itemView);
            tvAccountId = itemView.findViewById(R.id.tvAccountId);
            tvAccountSolde = itemView.findViewById(R.id.tvAccountSolde);
            tvAccountType = itemView.findViewById(R.id.tvAccountType);
        }
    }
}
