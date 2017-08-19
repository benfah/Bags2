package me.benfah.bags2.util;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import me.benfah.cu.util.ReflectionUtils;

public class JavassistUtil
{
    public static Class<?> injInventory3;
    
    
    public static Class<?> getCFurnaceClass()
    {
    	ClassPool cp = ClassPool.getDefault();
        cp.importPackage("me.benfah.cu.api");
         
         
         
        if(injInventory3 == null)
        injInventory3 = JavassistUtil.getFurnaceClass();
        return injInventory3;
    }
    
    private static Class<?> getFurnaceClass()
    {
         
        try {
            try {
            return Class.forName("me.benfah.gen.CustomTEFurnace");
            } catch (ClassNotFoundException e) {
                
            	Class<?> blockClass = ReflectionUtils.getRefClass("{nms}.Block");
            	Class<?> blocksClass = ReflectionUtils.getRefClass("{nms}.Blocks");

            	CtClass ctInjected = ClassPool.getDefault().makeClass("me.benfah.gen.CustomTEFurnace");
                 
                ctInjected.setSuperclass(ClassPool.getDefault().get(ReflectionUtils.getRefClass("{nms}.TileEntityFurnace").getName()));
                
                
                ctInjected.addMethod(CtMethod.make("public " + blockClass.getName() + " getBlock()"
                		+ "{"
                		+ "return " + blocksClass.getName() + ".LIT_FURNACE;"
                		+ "}", ctInjected));
                
                
                ctInjected.addMethod(CtMethod.make("public void burn()"
                		+ "{"
                		+ "try"
                		+ "{"
                		+ "super.burn();"
                		+ "}"
                		+ "catch(Exception e)"
                		+ "{"
                		+ "e.printStackTrace();"
                		+ "}"
                		+ "}", ctInjected));
                ctInjected.addMethod(CtMethod.make("public void e()"
                		+ "{"
                		+ "try"
                		+ "{"
                		+ "super.e();"
                		+ "}"
                		+ "catch(Exception e)"
                		+ "{"
                		+ "e.printStackTrace();"
                		+ "}"
                		+ "}", ctInjected));
                return ctInjected.toClass();
            }
             
        } catch (CannotCompileException | NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
