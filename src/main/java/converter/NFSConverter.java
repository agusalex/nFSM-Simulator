package converter;

import model.Tuple;

import java.util.*;

public class NFSConverter {

    private String q0;
    private Map<String, HashSet<Tuple<Character, String>>> trasitions;
    private List<String> finalStates;
    HashSet<Character> language;


    public static void main(String[] args) {
        System.out.println(FSMachineFactory.get().nfa2DfaPlainText("stringsFinishedWithA"));
    }

    public NFSConverter(String q0, Map<String, HashSet<Tuple<Character, String>>> trasitions,
                        List<String> finalStates, HashSet<Character> language) {


        this.q0 = q0;
        this.trasitions = trasitions;
        this.finalStates = finalStates;
        this.language = language;

    }


    private Set<String> getTransitionsOfChar(Set<Tuple<Character, String>> transition, Character c) {
        Set<String> filteredTransitions = new HashSet<>();
        transition.forEach(tuple -> {
            if (tuple.getFirst() == c) {
                filteredTransitions.add(tuple.getSecond());
            }
        });
        return filteredTransitions;
    }

    public String convert() {

        //<Q_d, \Sigma, q_o, F_d, \delta_d>

        Set<Set<String>> Q_d = new HashSet<>();
        Map<Set<String>, HashSet<Tuple<Character, Set<String>>>> transitions_d = new HashMap<>();

        Q_d.add(new HashSet<>(Arrays.asList(q0)));

        //for S : Q_d
        Set<Set<String>> Q_d_old = new HashSet<>();

        while (!Q_d_old.equals(Q_d)) {
         //  System.out.println("Q_D: " + Q_d);
            Set<Set<String>> diff = new HashSet<>(Q_d);
            diff.removeAll(Q_d_old);
            Q_d_old = new HashSet<>(Q_d);

            for (Set<String> S : Q_d) {
                //for symbol : \Sigma

                for (Character a : this.language) {
                    Set<String> dState = new HashSet<>();
                    for (String p : S) {

                       // System.out.println("P: " + p + " ,  A: " + a);
                        //Busco las transisicion donde charachter = a
                        if (this.trasitions.get(p) != null) {
                            Set<String> transitions_a = getTransitionsOfChar(this.trasitions.get(p), a);
                        //    System.out.println("Transitions_a: " + transitions_a);
                            dState.addAll(transitions_a);

                        }
                        //Luego, agrego los estados a los que voy a dState


                    }

                    //\delta_d (S.toString(), symbol) = U_{p \in S_n} \delta_n (p, symbol) = Set
                    Q_d.add(dState);
                    Tuple<Character, Set<String>> tuple = new Tuple<>(a, dState);
                    transitions_d.computeIfAbsent(S, k -> new HashSet<>());
                    transitions_d.get(S).add(tuple);
                  //  System.out.println("State: "+ S+"Tupla: "+ tuple );


                }

            }


        }

        System.out.println();
        String Q_d_s = Q_d.toString();
        String states = Q_d.toString().substring(1, Q_d_s.length() - 1);
        return "States: { " + states + " } \nTransitions: " + transitions_d + "\nLanguage: "+language+"\nInitial:"+q0+"\nFinal: "+finalStates;
    }

}
