package DuplicateCheck;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class AbsolutePath {  //�����ļ��ľ���·��
	@Test
	public void Main() {
		String[] paths = {

                "D:\\eclipse\\���Ĳ����㷨\\orig.txt",

                "D:\\eclipse\\���Ĳ����㷨\\orig_0.8_add.txt",

                "D:\\eclipse\\���Ĳ����㷨\\ans.txt"

        };

        try {

        	RepetitionRate.main(paths);

        } catch (IOException e) {

            e.printStackTrace();

        }
	}
}
