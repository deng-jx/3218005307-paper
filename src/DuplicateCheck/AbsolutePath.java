package DuplicateCheck;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class AbsolutePath {  //三个文件的绝对路径
	@Test
	public void Main() {
		String[] paths = {

                "D:\\eclipse\\论文查重算法\\orig.txt",

                "D:\\eclipse\\论文查重算法\\orig_0.8_add.txt",

                "D:\\eclipse\\论文查重算法\\ans.txt"

        };

        try {

        	RepetitionRate.main(paths);

        } catch (IOException e) {

            e.printStackTrace();

        }
	}
}
