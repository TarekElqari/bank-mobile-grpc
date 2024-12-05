package ma.emsi.bankgrpc.activities;



import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ma.emsi.bankgrpc.R;
import ma.emsi.bankgrpc.client.CompteServiceClient;
import ma.emsi.bankgrpc.stubs.SaveCompteResponse;
import ma.emsi.bankgrpc.stubs.TypeCompte;

public class AddAccountActivity extends AppCompatActivity {

    private EditText etSolde, etDateCreation;
    private Spinner spinnerType;
    private Button btnSaveAccount;

    private CompteServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        etSolde = findViewById(R.id.etSolde);
        etDateCreation = findViewById(R.id.etDateCreation);
        spinnerType = findViewById(R.id.spinnerType);
        btnSaveAccount = findViewById(R.id.btnSaveAccount);

        client = new CompteServiceClient("10.0.2.2", 9090);

        btnSaveAccount.setOnClickListener(v -> saveAccount());
    }

    private void saveAccount() {
        String soldeStr = etSolde.getText().toString();
        String dateCreation = etDateCreation.getText().toString();
        String typeStr = spinnerType.getSelectedItem().toString();

        if (soldeStr.isEmpty() || dateCreation.isEmpty() || typeStr.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        float solde = Float.parseFloat(soldeStr);
        TypeCompte type = typeStr.equals("COURANT") ? TypeCompte.COURANT : TypeCompte.EPARGNE;

        new Thread(() -> {
            try {
                SaveCompteResponse response = client.saveCompte(solde, dateCreation, type);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Account Saved: " + response.getCompte().getId(), Toast.LENGTH_LONG).show();
                    finish();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to save account", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
