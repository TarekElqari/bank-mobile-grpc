package ma.emsi.bankgrpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ma.emsi.bankgrpc.stubs.CompteServiceGrpc;
import ma.emsi.bankgrpc.stubs.GetAllComptesRequest;
import ma.emsi.bankgrpc.stubs.GetAllComptesResponse;
import ma.emsi.bankgrpc.stubs.GetCompteByIdRequest;
import ma.emsi.bankgrpc.stubs.GetCompteByIdResponse;
import ma.emsi.bankgrpc.stubs.GetTotalSoldeRequest;
import ma.emsi.bankgrpc.stubs.GetTotalSoldeResponse;
import ma.emsi.bankgrpc.stubs.SaveCompteRequest;
import ma.emsi.bankgrpc.stubs.SaveCompteResponse;
import ma.emsi.bankgrpc.stubs.TypeCompte;

public class CompteServiceClient {

    private final CompteServiceGrpc.CompteServiceBlockingStub blockingStub;

    public CompteServiceClient(String serverAddress, int serverPort) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext() // Disable TLS for development
                .build();

        blockingStub = CompteServiceGrpc.newBlockingStub(channel);
    }

    // Method to retrieve all accounts
    public GetAllComptesResponse getAllComptes() {
        return blockingStub.allComptes(
                GetAllComptesRequest.newBuilder().build()
        );
    }

    // Method to retrieve an account by ID
    public GetCompteByIdResponse getCompteById(String id) {
        return blockingStub.compteById(
                GetCompteByIdRequest.newBuilder()
                        .setId(id)
                        .build()
        );
    }

    // Method to get total balance statistics
    public GetTotalSoldeResponse getTotalSolde() {
        return blockingStub.getTotalSolde(
                GetTotalSoldeRequest.newBuilder().build()
        );
    }

    // Method to save a new account
    public SaveCompteResponse saveCompte(float solde, String dateCreation, TypeCompte type) {
        SaveCompteRequest compteRequest = SaveCompteRequest.newBuilder()
                .setCompte(
                        ma.emsi.bankgrpc.stubs.CompteRequest.newBuilder()
                                .setSolde(solde)
                                .setDateCreation(dateCreation)
                                .setType(type)
                                .build()
                )
                .build();

        return blockingStub.saveCompte(compteRequest);
    }

}
