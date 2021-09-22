package com.todo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void createItem(TodoList list) {

		String title, desc;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); //선언

		System.out.println("------일정 추가------");
		
		try {
			System.out.print("[제목] ");
			title = bf.readLine();
			if (list.isDuplicate(title)) {
				System.out.printf("제목은 중복될 수 없습니다 ㅠ\n");
				return;
			}

			System.out.print("[설명] ");
			desc = bf.readLine();

			TodoItem t = new TodoItem(title, desc);
			list.addItem(t);
			
			System.out.println("\" <"+title+"> "+desc+" \"이(가) 저장되었습니다 :)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void deleteItem(TodoList l) {

		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); //선언

		System.out.println("------일정 삭제------");

		String title;
		boolean notExist = true;
		try {
			System.out.println("삭제할 일정의 제목을 입력하세요");
			System.out.print("[제목] ");
			title = bf.readLine();
			for (TodoItem item : l.getList()) {
				if (title.equals(item.getTitle())) {
					notExist = false;
					l.deleteItem(item);
					System.out.println("일정 \" <"+title +"> \"" + "이(가) 삭제되었습니다 :)");
					break;
				}
			}
			if(notExist) System.out.println("일정 \" <"+title +"> \"" + "은(는) 존재하지 않습니다");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateItem(TodoList l) {

		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); //선언

		System.out.println("------일정 수정------");
		String title;
		try {
			System.out.println("수정할 일정의 제목을 입력하세요");
			System.out.print("[제목] ");
			title = bf.readLine().trim();

			if (!l.isDuplicate(title)) {
				System.out.println("일정 \" <"+title +"> \"" + "은(는) 존재하지 않습니다");
				return;
			}

			System.out.print("[변경할 제목] ");
			String new_title = bf.readLine().trim();
			if (l.isDuplicate(new_title)) {
				System.out.printf("제목은 중복될 수 없습니다 ㅠ\n");
				return;
			}
			System.out.print("[변경할 설명] ");
			String new_description = bf.readLine().trim();
			
			for (TodoItem item : l.getList()) {
				if (item.getTitle().equals(title)) {
					l.deleteItem(item);
					TodoItem t = new TodoItem(new_title, new_description);
					l.addItem(t);
					System.out.println("해당 일정이 \" <"+new_title+"> "+new_description+" \"로 변경되었습니다 :)");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void listAll(TodoList l) {
		System.out.println("------전체 목록------");
		for (TodoItem item : l.getList()) {
			System.out.println("<"+ item.getTitle() + "> " + item.getDesc() + " - " + item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			String filePath = Paths.get(".").toAbsolutePath().toString() +"/"+ filename;
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
			for(TodoItem item : l.getList()) {
				bw.write(item.toSaveString());
//                bw.newLine();
                bw.flush();
			}
            bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) throws FileNotFoundException {
		
		try {
			String filePath = Paths.get(".").toAbsolutePath().toString() +"/"+ filename;
//			System.out.println(Paths.get(".").toAbsolutePath().toString());
//			System.out.println(filePath);
			File file = new File(filePath);
			
			if(file.exists()) {
	            BufferedReader br = new BufferedReader(new FileReader(file));

	            String line = "";
	            while ((line = br.readLine()) != null) {
	                StringTokenizer st = new StringTokenizer(line, "##");
	                String title = st.nextToken().trim();
	                String desc = st.nextToken().trim();
	                String current_date = st.nextToken().trim();

	                l.addItem(new TodoItem(title,desc,current_date));
	            }
	            System.out.println("파일 로딩 완료!");
	            br.close();
	        }
	        else {
	            System.out.println("파일 없음");
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e){
			e.printStackTrace();
        } 
		
	}
}
