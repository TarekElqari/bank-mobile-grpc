package ma.emsi.tpgrpcbanque.controllers;

import io.grpc.stub.StreamObserver;
import ma.emsi.tpgrpcbanque.stubs.*;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@GrpcService
public class CompteServiceImpl extends CompteServiceGrpc.CompteServiceImplBase {

    private final Map<String, Compte> compteDB = new ConcurrentHashMap<>();

    @Override
    public void allComptes(GetAllComptesRequest request,
                           StreamObserver<GetAllComptesResponse> responseObserver) {
        try {
            GetAllComptesResponse.Builder responseBuilder = GetAllComptesResponse.newBuilder();
            responseBuilder.addAllComptes(compteDB.values());
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void compteById(GetCompteByIdRequest request,
                           StreamObserver<GetCompteByIdResponse> responseObserver) {
        try {
            Compte compte = compteDB.get(request.getId());
            if (compte != null) {
                responseObserver.onNext(GetCompteByIdResponse.newBuilder()
                        .setCompte(compte).build());
                responseObserver.onCompleted();
            } else {

                responseObserver.onError(new Throwable("Compte non trouvé"));
            }
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getTotalSolde(GetTotalSoldeRequest request, StreamObserver<GetTotalSoldeResponse> responseObserver) {
        try {
            int count = compteDB.size();
            float sum = 0;
            for (Compte compte : compteDB.values()) {
                sum += compte.getSolde();
            }
            float average = count > 0 ? sum / count : 0;

            SoldeStats stats = SoldeStats.newBuilder()
                    .setCount(count)
                    .setSum(sum)
                    .setAverage(average)
                    .build();

            responseObserver.onNext(GetTotalSoldeResponse.newBuilder()
                    .setStats(stats).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void saveCompte(SaveCompteRequest request,
                           StreamObserver<SaveCompteResponse> responseObserver) {
        try {
            CompteRequest compteReq = request.getCompte();
            String id = UUID.randomUUID().toString();

            Compte compte = Compte.newBuilder()
                    .setId(id)
                    .setSolde(compteReq.getSolde())
                    .setDateCreation(compteReq.getDateCreation())
                    .setType(compteReq.getType())
                    .build();

            compteDB.put(id, compte);

            responseObserver.onNext(SaveCompteResponse.newBuilder()
                    .setCompte(compte).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
