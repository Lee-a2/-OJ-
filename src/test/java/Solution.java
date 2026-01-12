public class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] a={1,2};
        return a;
    }
    public int lengthOfLongestSubstring(String s) {
        return 0;
    }
    public static void main(String[] args) {
        Solution solution=new Solution();
        String s="abcabcbb";
        int count=solution.lengthOfLongestSubstring(s);
        if (count==3){
            System.out.println("testcase1 Ok!");
        }else {
            System.out.println("testcase1 failed!!");

        }
        String s2="bbbbb";
        int count1=solution.lengthOfLongestSubstring(s2);
        if (count1==1){
            System.out.println("testcase2 Ok!");
        }else {
            System.out.println("testcase2 failed!!");

        }
        String s3="pwwkew";
        int count3=solution.lengthOfLongestSubstring(s3);
        if (count3==3){
            System.out.println("testcase3 Ok!");
        }else {
            System.out.println("testcase3 failed!!");

        }
    }
    public static void main1(String[] args) {
        Solution solution=new Solution();
        int[] nums={2,7,11,15};
        int target=9;
        int[] result=solution.twoSum(nums,target);
        if (result.length==2&&result[0]==0&&result[1]==1){
            System.out.println("testcase1 Ok!");
        }else {
            System.out.println("testcase1 failed!");
        }
        int[] nums2={3,2,4};
        int target2=6;
        int[] result2=solution.twoSum(nums2,target2);
        if (result2.length==2&&result[0]==1&&result[1]==2){
            System.out.println("testcase2 Ok!");
        }else {
            System.out.println("testcase2 failed!");
        }
    }
}
