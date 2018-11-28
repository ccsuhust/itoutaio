package com.iexample.itoutaio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class ItoutaioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItoutaioApplication.class, args);
	}


	/*public static  void demoCommon(){
		Random r = new Random(1);
		for(int i=0;i<5;i++)
		{
			print("random",r.nextInt(100));
		}

		List<Integer> list= Arrays.asList(new Integer[]{1,2,3,4,2,8,0});
		print("arrays",list);
		Collections.shuffle(list);
		print("arrays",list);


		System.out.println(d.getTime());

		DateFormat sdf = new SimpleDateFormat("yyy/MM/dd hh:mm:ss");
		Date d = new Date();
		System.out.println(sdf.format(d));
		print("Date",DateFormat.getDateInstance().format(d));

		print("UUID",UUID.randomUUID());
		Thread thred = new Thread(){
			@Override
			public void run() {
				while(true){
					Date d = new Date();
					try {
						Thread.sleep(1000);
						System.out.println(sdf.format(d));
					}catch (Exception e){
						e.printStackTrace();
					}

				}

			}
		};
		thred.start();
		while(true){
			try {
				Thread.sleep(100);
				System.out.println("hello");
			}catch (Exception e){
				e.printStackTrace();
			}

		}


	}
	public  static void testSet()
	{
		Set<Integer> s= new HashSet();
		for(int i=0;i<5;i++)
		{
			s.add(i);
			s.add(i);
		}
		print(23,s);
		s.remove(2);
		for(int i:s)
		{
			System.out.print(String.format("%d:",i));
		}
		System.out.println();
		s.addAll(Arrays.asList(new Integer[]{1,2,3,7,8,9,0}));
		for(int i:s)
		{
			print(24,i);
		}
	}
	static void  print(int i,Object Obj)
	{
		System.out.println(i+": "+Obj.toString());
	}
	static void  print(String s,Object Obj)
	{
		System.out.println(s+": "+Obj.toString());
	}
	public static void testMap()
	{
		Map<String,String> m = new HashMap<>();
		m.put("21","34");
		m.put("22","34");
		for(int i=0;i<4;i++)
		{
			m.put(String.valueOf(i),String.valueOf(i*i));
		}
		for(Map.Entry entry:m.entrySet())
		{
			print("map ",entry.getKey()+":"+entry.getValue());
		}
		print("keys",m.keySet());
		print("values",m.values());
		print("map",m);
		print("contain 2",m.containsKey("2"));
		print("contain 22",m.containsKey("22"));
		m.replace("1","1111");

	}
	public static void testString(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("23");
		sb.append("23");
		sb.append(23);
		System.out.println(sb.toString());


		List<String> str = new ArrayList<>();
		for(int i =0;i<4;i++)
		{
			str.add(String.valueOf(i));
		}
		print(1,str);
		List<String> strr = new ArrayList<>();
		for(int i =0;i<4;i++)
		{
			strr.add(String.valueOf(i+1));
		}
		str.addAll(strr);
		print(2,str);
		str.remove(String.valueOf(1));
		print(21,str);

		Map<String,String> s = new HashMap<>();
		for(int i =0;i<4;i++)
		{
			s.put(String.valueOf(i),String.valueOf(i*i));
		}
		print(3,s);
		print(4,"8".compareTo("8"));
		print(4,"8".compareTo("9"));
		print(4,"8".compareTo("7"));
		str.add("23");
		print(2,str);
		Collections.sort(str);
		print(2,str);

		Collections.sort(str, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
		});
		print(3,str);
		Collections.reverse(str);
		print(4,str);

		print(8,String.valueOf(9));
		Integer[] a=new Integer[]{1,2,3};
		print(8,Arrays.asList(a));
		int[] aa=new int[]{3,5};
		print(9,Arrays.asList(aa));
	}*/
}
