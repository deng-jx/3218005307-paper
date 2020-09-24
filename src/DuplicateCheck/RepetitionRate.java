package DuplicateCheck;

import java.io.*;
import java.util.*;

public class RepetitionRate {
	private static List<String> getCoupleWords(String paragraph) {
		List<String> coupleWordsList = new ArrayList<String>();
		try {
			String[] wordsList = paragraph.split("");
			String words = "";
			for (int i = 0; i < wordsList.length - 1; i++) {
				words = wordsList[i] + wordsList[i + 1];
				coupleWordsList.add(words);
			}
		} catch(Exception e) {
			System.out.println("路径出错，操作失败");
			System.exit(0);
		}
		return coupleWordsList;
	}
	
	public static ArrayList<String> getArticle(String path) throws IOException{
		ArrayList<String> res = new ArrayList<String>();  //段落的划分
		StringBuilder sb = new StringBuilder();  //拼接读取的内容
		String temp1 = null;  //临时变量，存储sb没有去除标点符号的内容(只是去除空格)
		String temp = null;  //临时变量，存储sb去除空格的内容并且取出标点符号的内容
		BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
		
		int ch = 0;
		while((ch = reader.read()) != -1) {
			temp1 = sb.toString().trim().replaceAll("\\s*", "");
			temp = temp1.replaceAll("[\\pP\\p{Punct}]", "");
			
			if((char)ch == '\r') {  //判断是否存在空格
				if(!"".equals(temp)) {
					res.add(temp);  //到了段落结尾，将其加入到链表，并清空sb
				}
				sb.delete(0, sb.length());  //清空sb
			}else {
				sb.append((char)ch);  //没到段落结尾，将结果暂存
			}
		}
		if(reader.read() == -1) {
            System.out.println("paragraphEnd");
        }
        if(!"".equals(temp)) {   // 最后一段如果非空, 那就把最后一段加上, 否则不处理
        	res.add(temp);
        }
        reader.close();
		return res;
   }
	
	public static void Check(String originPath,Set<String> dictionary,String copyPath,FileWriter fileWrite) throws IOException{
		long startTime = System.currentTimeMillis();  //获取开始时间
		ArrayList<String> copy = getArticle(copyPath);  // 抄袭文本
		List<String> copyTxtListItem = null;  // 针对一段  两两分组
		//查重
		float allWordsNum = 0;  //抄袭文本总数
		float similarWords = 0;  // 相似数
		for(int i = 0; i < copy.size(); i ++) {
			copyTxtListItem = getCoupleWords(copy.get(i));
			allWordsNum += copyTxtListItem.size();
			for(int n = 0; n < copyTxtListItem.size(); n ++) {
				if(dictionary.contains(copyTxtListItem.get(n))) {
					similarWords ++;
				}
			}
		}
		float similar = similarWords/allWordsNum;
		long endTime = System.currentTimeMillis();    //获取结束时间
		System.out.println("原文路径:" + originPath + "		抄袭论文路径:" + copyPath + "\n查重率:" + String.format("%.2f",similar) + "\nִ执行时间" + (endTime - startTime) + "ms");		
		// 写入文件
		fileWrite.append("原文路径:" + originPath + "		抄袭论文路径:" + copyPath + "\n查重率:" + String.format("%.2f",similar) + "\n\n");
	}
	
	public static void main(String[] args) throws IOException{
		String originPath; //原文路径
		String copyPath; //抄袭文本路径
		String filePath;  //答案路径
		String[] parms;  //接受参数
		
		if(args.length != 0) {
			parms = args;
		} else {
			System.out.println("请给出原文绝对路径，抄袭版论文绝对路径，输出答案文件的绝对路径【参数按顺序输入，并用用空格隔开】");
			Scanner scanner = new Scanner(System.in);
			parms = scanner.nextLine().split(" ");
			if(parms.length < 3) {
				System.out.println("操作错误，请正确输入参数");
				System.exit(0);
			}
			scanner.close();
		}
		//新建文件
		filePath = parms[2];
		File resFile = new File(filePath);
		if(!resFile.exists()) {
			try {
				resFile.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		//写入文件
		FileWriter fileWrite = new FileWriter(resFile, true);
		originPath = parms[0];  //D:\eclipse\论文查重算法\orig.txt
		copyPath=parms[1];  //D:\eclipse\论文查重算法\orig_0.8_add.txt  orig_0.8_del  orig_0.8_dis_1  orig_0.8_dis_10  orig_0.8_dis_15
		System.out.println();
		// 原文本
		ArrayList<String> origin=getArticle(originPath);  //得到段落数组
		// 根据原文生成词典
		Set<String> dictionary = new HashSet<String>();
		List<String> dictionaryItem = null;
		for(int j = 0; j < origin.size(); j ++) {
			dictionaryItem = getCoupleWords(origin.get(j));
			for(int k = 0; k < dictionaryItem.size(); k ++) {
				dictionary.add(dictionaryItem.get(k));
			}
		}
		Check(originPath, dictionary, copyPath, fileWrite);
		fileWrite.close();
		System.out.println("\n查询结果已写入" + filePath);
	}
}
