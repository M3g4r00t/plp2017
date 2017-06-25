package concurrente_lista;

import java.util.Random;

/**
 *
 * @author Dennys
 */
public class CompeticaoExemplo {

    private int recursoCompartilhado;
    private final Object monitorCompeticao = new Object();

    void operacao() {
        synchronized (monitorCompeticao) {
            int aux;
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou ler");
            simularDemora(2000);
            aux = recursoCompartilhado; //ler
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Eu li: " + aux);
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou efetuar");
            simularDemora(2000);
            aux = aux + 1;              //efetuar
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Eu efetuei: " + aux);
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou atribuir");
            simularDemora(2000);
            recursoCompartilhado = aux; //atribuir
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Eu fiz atribucao: " + recursoCompartilhado);
        }
    }

    public static void main(String[] args) {
        CompeticaoExemplo competenca = new CompeticaoExemplo();
        //Definicao dos Threads 
        Thread threadCompetidor1 = new Thread(() -> {
            competenca.operacao();
        });
        Thread threadCompetidor2 = new Thread(() -> {
            competenca.operacao();
        });
        Thread threadCompetidor3 = new Thread(() -> {
            competenca.operacao();
        });

        //Nome dos Threads
        threadCompetidor1.setName("Competidor 1");
        threadCompetidor2.setName("Competidor 2");
        threadCompetidor3.setName("Competidor 3");

        //Vamos competir!
        threadCompetidor1.start();
        threadCompetidor2.start();
        threadCompetidor3.start();

        //Vou imprimir estados dos Threads
        while (threadCompetidor1.isAlive() || threadCompetidor2.isAlive() || threadCompetidor3.isAlive()) {
            System.out.println("Estado dos threads: " + threadCompetidor1.getName() + ": " + threadCompetidor1.getState() + " | " + threadCompetidor2.getName() + ": " + threadCompetidor2.getState() + " | " + threadCompetidor3.getName() + ": " + threadCompetidor3.getState());
            simularDemora(500);
        }
    }

    static void simularDemora(int tempo) {
        try {
            int tempoRandom = Math.abs((new Random()).nextInt(tempo));
            Thread.sleep(tempoRandom);
        } catch (InterruptedException ex) {
        }
    }
}
