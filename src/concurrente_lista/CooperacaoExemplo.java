package concurrente_lista;

import java.util.Random;

/**
 *
 * @author Dennys
 */
public class CooperacaoExemplo {

    private double recursoCompartilhado;
    private final Object semaforoRecursoCompartilhado = new Object();
    private volatile boolean flagRecursoCompartilhado = false;

    void produzir() {
        double aux;
        synchronized (semaforoRecursoCompartilhado) {
            while (flagRecursoCompartilhado) {              //While é mais geral que if
                try {
                    semaforoRecursoCompartilhado.wait();
                } catch (InterruptedException ex) {
                }
            }
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou calcular");
            simularDemora(2000);
            aux = Math.PI * Math.exp(Math.random() * 10);   //Calular
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Calculei: " + aux);
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou atribuir");
            simularDemora(2000);
            recursoCompartilhado = aux;                     //Atribuir 
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Atribui: " + recursoCompartilhado);
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou tornar semaforo em verde");
            simularDemora(2000);
            flagRecursoCompartilhado = true;                //Condicao de producao
            semaforoRecursoCompartilhado.notifyAll();       //Notificar
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Tornei semaforo em verde");
        }
    }

    void consumir() {
        double aux;
        synchronized (semaforoRecursoCompartilhado) {
            while (!flagRecursoCompartilhado) {                 //While é mais geral que if
                try {
                    semaforoRecursoCompartilhado.wait();        //Aguardar
                } catch (InterruptedException ex) {
                }
            }
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou ler");
            simularDemora(2000);
            aux = recursoCompartilhado;                         //Ler
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Eu li: " + aux);
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou consumir");
            simularDemora(2000);
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Consumi: " + recursoCompartilhado);
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Vou tornar semaforo em verde");
            simularDemora(2000);
            flagRecursoCompartilhado = false;                //Condicao de producao
            semaforoRecursoCompartilhado.notifyAll();        //Semaforo em verde
            System.out.println("Thread: " + Thread.currentThread().getName() + " -> Tornei semaforo em verde");
        }
    }

    public static void main(String[] args) {
        CooperacaoExemplo cooperacao = new CooperacaoExemplo();
        //Definicao dos Threads 
        Thread produtor1 = new Thread(() -> {
            cooperacao.produzir();
        });
        Thread produtor2 = new Thread(() -> {
            cooperacao.produzir();
        });
        Thread produtor3 = new Thread(() -> {
            cooperacao.produzir();
        });
        Thread consumidor1 = new Thread(() -> {
            cooperacao.consumir();
        });
        Thread consumidor2 = new Thread(() -> {
            cooperacao.consumir();
        });
        Thread consumidor3 = new Thread(() -> {
            cooperacao.consumir();
        });

        //Nome dos Threads
        produtor1.setName("Produtor 1");
        produtor2.setName("Produtor 2");
        produtor3.setName("Produtor 3");
        consumidor1.setName("Consumidor 1");
        consumidor2.setName("Consumidor 2");
        consumidor3.setName("Consumidor 3");

        //Vamos cooperar!
        produtor1.start();
        produtor2.start();
        produtor3.start();
        consumidor1.start();
        consumidor2.start();
        consumidor3.start();

        //Vou imprimir estados dos Threads
        while (produtor1.isAlive() || produtor2.isAlive() || produtor3.isAlive() || consumidor1.isAlive() || consumidor2.isAlive() || consumidor3.isAlive()) {
            System.out.println("Estado dos threads: " + produtor1.getName() + ": " + produtor1.getState() + " | " + produtor2.getName() + ": " + produtor2.getState() + " | " + produtor3.getName() + ": " + produtor3.getState() + " | " + consumidor1.getName() + ": " + consumidor1.getState() + " | " + consumidor2.getName() + ": " + consumidor2.getState() + " | " + consumidor3.getName() + ": " + consumidor3.getState() + " | ");
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
