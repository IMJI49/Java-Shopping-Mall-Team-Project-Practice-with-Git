package com.shoppingmall.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
 
public class FileManagement {
	//data 디렉토리 경로
	private static final String DATA_DIR = "data/";
	
	
	//파일에서 객체 리스트 읽기
	public static <T> List<T> readFromFile(String fileName){
		//파일 경로
		String fullPath = normalizePath(fileName);
		File file = new File(fullPath);
		
		//파일이 존재하지 않는 경우
		if(!file.exists()) {
			System.err.println("파일이 존재하니 않습니다: " + fullPath);
			System.out.println("빈 리스트를 반환합니다.");
			return new ArrayList<>();
		}
		
		//파일이 비어있는 경우
		if(file.length() == 0) {
			System.err.println("파일이 비어있습니다: " + fullPath);
			System.out.println("빈 리스트를 반환합니다.");
			return new ArrayList<>();
		}
		
		// 파일 읽기
		try (ObjectInputStream ois = new ObjectInputStream(
									 	new FileInputStream(file))){
			@SuppressWarnings("unchecked")
			List<T> data = (List<T>) ois.readObject();
			System.out.println("파일 읽기 성공: " + fullPath + " (" + data.size()+ "개 항목)");
			return data;
			
		} catch (IOException e) {
			System.err.println("파일 읽기 실패: " + fullPath);
			System.out.println(" 오류 내용: " + e.getMessage());
			return new ArrayList<>();
		} catch (Exception e) {
			System.err.println("파일 읽기 실패: " + fullPath);
			System.out.println(" 오류 내용: " + e.getMessage());
			return new ArrayList<>();
		}
		
	}

	
	// 파일 경로 정규화 (운영체제와 관련없이 올바른 경로 반환)
	private static String normalizePath(String fileName) {
		//이미 경로가 포함된 경우 그대로 반환
		if(fileName.contains(File.separator) || fileName.contains("\\") || fileName.contains("/")) {
			return fileName;
		}
		
		//그렇지 않은 경우, data 디렉토리 포함 전체 경로 반환
		return DATA_DIR + File.separator + fileName;
	}
	
	
	// 객체 리스트를 파일에 저장
	public static <T> void writeToFile(String fileName, List<T> data) {
		// null 체크
		if(data == null) {
			System.err.println("[FileNamanger] 저장할 데이터가 null입니다.");
			return;
		}

		// 파일 경로
		String fullPath = normalizePath(fileName);
		File file = new File(fullPath);
		
		// 부모 디렉토리 확인 및 생성
		File parentDir = file.getParentFile();
		
		// parentDir가 null이 아니고, 존재하지 않는 경우에만 생성
		if(parentDir != null && !parentDir.exists()) {
			Boolean created = parentDir.mkdirs();
			if(created) {
				System.out.println("디렉토리 생성: " + parentDir.getPath());	
			}
		}
		
		// 파일 저장 시도
		try(ObjectOutputStream oos = new ObjectOutputStream(
										new FileOutputStream(file))){
			oos.writeObject(data);
			oos.flush();  // 버퍼 강제
			
			System.out.println("파일 저장 성공: " +fullPath+ 
					"(" +data.size()+ "개 항목)");
		} catch (IOException e) {
			System.err.println("파일 저장 실패: " + fullPath);
			System.out.println(" 오류 내용: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}












