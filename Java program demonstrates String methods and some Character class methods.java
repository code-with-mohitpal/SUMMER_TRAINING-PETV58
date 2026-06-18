class x
{
    public static void main(String arg[])
    {
        String s = "hello java";

        System.out.println(s.length());
        System.out.println(s.charAt(1));

        s = s.concat(" welcome");
        System.out.println(s);

        StringBuilder sb = new StringBuilder("how r u");
        sb.append(" my students");
        System.out.println(sb);

        s = s.toUpperCase();
        System.out.println(s);

        char ch = 'd';
        char newch = Character.toUpperCase(ch);
        System.out.println(newch);

        String s1 = "rame";
        String s2 = "Rame";

        System.out.println(s1.equalsIgnoreCase(s2));

        System.out.println(s1.contains("am"));
        System.out.println(s2.endsWith("me"));

        String str = "LPU Punjab";

        System.out.println(str.substring(4));
        System.out.println(str.substring(4, 7));

        String str1 = "   he llo      ";

        System.out.println("Before: " + str1.length());

        str1 = str1.trim();

        System.out.println("After: " + str1.length());

        String s5 = "lpu is one university";
        System.out.println(s5.indexOf("o"));

        String s6 = "hello what are you doing";
        String words[] = s6.split(" ");
        System.out.println(words[1]);

        String s7 = "hello-what-are-you-doing";
        String word[] = s7.split("-");
        System.out.println(word[2]);
    }
}
