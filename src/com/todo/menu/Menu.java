package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("1. add - 일정 추가");
        System.out.println("2. del - 일정 삭제");
        System.out.println("3. edit - 일정 수정");
        System.out.println("4. ls - 목록 보기");
        System.out.println("5. ls_name_asc - 제목순으로 정렬");
        System.out.println("6. ls_name_desc - 제목역순으로 정렬");
        System.out.println("7. ls_date - 날짜순 정렬");
        System.out.println("8. exit - 종료");
    }
    
    public static void prompt()
    {
        System.out.print("\nCommand > ");
    }
}
