package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) throws IOException {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);	
		
		System.out.print("Entre com o caminho do arquivo: ");    // /home/jose/projetos-ds/in.csv
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			System.out.println(path);
			List<Sale> list = new ArrayList<>();
		
			String line = br.readLine();
			
			while(line != null) {
				
				String [] fields = line.split(",");
				
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				
				list.add(new Sale(month, year, seller, items, total));
				
				line = br.readLine();	
			}
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			
			Comparator<Sale> comp = (c1, c2) -> c1.averagePrice().compareTo(c2.averagePrice());
			
			list.stream()
			.sorted(comp.reversed())
			.filter(p -> p.getYear() == 2016)
			.limit(5)	
			.forEach(e -> System.out.println(e));
			
			System.out.println();
			System.out.print("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = ");
			
			double total = list.stream()
					.filter(f -> f.getSeller().equals("Logan") && (f.getMonth() == 1 || f.getMonth() == 7))
					.mapToDouble(m -> m.getTotal())
					.reduce(0,(x, y) ->  x + y);
			
			System.out.println(total);
			
		} 
		catch (FileNotFoundException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		

	}

}
