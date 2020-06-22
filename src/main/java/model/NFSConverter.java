package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NFSConverter {
	
	private ArrayList<String> q0;
	private Map<String, HashSet<Tuple<Character, String>>> trasitions;
	private List<String> finalStates;
	HashSet<Character> language;


	
	
	public NFSConverter(ArrayList<String> q0, Map<String, HashSet<Tuple<Character, String>>> trasitions,
			List<String> finalStates, HashSet<Character> language) {

		this.q0 = q0;
		this.trasitions = trasitions;
		this.finalStates = finalStates;
		this.language = language;
		
	}

	public String convert() {
		
		//<Q_d, \Sigma, q_o, F_d, \delta_d>
		
		Set<Set<String>> Q_d = new HashSet<>();
		Map<String, HashSet<Tuple<Character, String>>> trasitions_d = new HashMap<>(); 
		
		Q_d.add(new HashSet<String>(Arrays.asList(q0.get(0))));
		
		//for S : Q_d
		for(Set<String> S : Q_d)
			//for symbol : \Sigma
			//\delta_d (S.toString(), symbol) = U_{p \in S_n} \delta_n (p, symbol) = Set 
			for(Character a : this.language) {
				
				Set<String> dState = new HashSet<>();
				
				for (String p : S) {
					
					Set<Tuple<Character, String>> trasition = this.trasitions.get(p);
					//Busco las transisicion donde charachter = a
					//Luego, agrego los estados a los que voy a dState
					
				}
				
				//Q_d.add(dState)
					
			}
		
		
		return null;
		
		
	}
	

}
