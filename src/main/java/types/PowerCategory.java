package types;

public class PowerCategory
{
    public static enum Categories {machine,weapon,spower}

    public static Categories toPowerCategory(String par)
    {
        if(par.equals("machine"))
            return Categories.machine;
        if(par.equals("weapon"))
            return Categories.weapon;
        if(par.equals("spower"))
            return Categories.spower;
        return null;
    }

}
