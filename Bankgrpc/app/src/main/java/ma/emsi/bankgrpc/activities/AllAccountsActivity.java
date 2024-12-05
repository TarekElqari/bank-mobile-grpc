package ma.emsi.bankgrpc.activities;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ma.emsi.bankgrpc.R;
import ma.emsi.bankgrpc.adapters.AccountAdapter;
import ma.emsi.bankgrpc.client.CompteServiceClient;
import ma.emsi.bankgrpc.stubs.GetAllComptesResponse;

public class AllAccountsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AccountAdapter adapter;
    private CompteServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_accounts);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AccountAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        client = new CompteServiceClient("10.0.2.2", 9090);
        fetchAllAccounts();
    }

    private void fetchAllAccounts() {
        new Thread(() -> {
            try {
                GetAllComptesResponse response = client.getAllComptes();
                runOnUiThread(() -> adapter.updateAccounts(response.getComptesList()));
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to fetch accounts", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
