package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NFSConverter {
	
	private String q0;
	private Map<String, HashSet<Tuple<Character, String>>> trasitions;
	private List<String> finalStates;
	HashSet<Character> language;


	
	
	public NFSConverter(String q0, Map<String, HashSet<Tuple<Character, String>>> trasitions,
			List<String> finalStates, HashSet<Character> language) {

		this.q0 = q0;
		this.trasitions = trasitions;
		this.finalStates = finalStates;
		this.language = language;
		
	}


	private Set<String> getTransitionsOfChar(Set<Tuple<Character, String>> transitions, Character c){
		Set<String> filteredTransitions = new HashSet<>();
		transitions.forEach(tuple->{
			if(tuple.getFirst()==c){
				filteredTransitions.add(tuple.getSecond());
			}
		});
		return filteredTransitions;
	}

	public String convert() {
		
		//<Q_d, \Sigma, q_o, F_d, \delta_d>
		
		Set<Set<String>> Q_d = new HashSet<>();
		Map<Set<String>, Tuple<Character, Set<String>>> transitions_d = new HashMap<>(); 
		
		Q_d.add(new HashSet<String>(Arrays.asList(q0)));
		
		//for S : Q_d
		for(Set<String> S : Q_d)
			//for symbol : \Sigma
			
			for(Character a : this.language) {
				
				Set<String> dState = new HashSet<>();
				
				for (String p : S) {

					//Busco las transisicion donde charachter = a
					Set<String> transitions_a = getTransitionsOfChar(this.trasitions.get(p),a);
					//Luego, agrego los estados a los que voy a dState
					dState.addAll(transitions_a);
					
				}
				
				//\delta_d (S.toString(), symbol) = U_{p \in S_n} \delta_n (p, symbol) = Set
				
				Tuple<Character, Set<String>> tuple = new Tuple<>(a, dState);				
				transitions_d.put(dState, tuple);					
				
			}
		final String[] temp = {""};
		Q_d.forEach(state->{
			temp[0] += state.toString();
		});
		String outputStates = temp[0].substring(1,temp[0].length()-1);

		return null;
	}
	
}
