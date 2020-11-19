public class BubbleSorting {
    public static void main(String[] args) {
        int[] nums = { 2, 4, 1, 56, 34, 42 };
        for (int num : bubble(nums)) {
            System.out.print(num + "\t");
        }
    }

    public static int[] bubble(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
        return nums;
    }

    public static int[] bubbleUpgrade(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            int flag = 0; // 最优，即数组有序
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    int tmp = nums[i];
                    nums[j] = nums[i];
                    nums[i] = tmp;
                    flag = 1;
                }
            }
            if (flag == 0) {
                break;
            }
        }
        return nums;
    }
}