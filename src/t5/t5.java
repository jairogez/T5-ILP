package t5;

import javassist.*;

public class t5 {
	public static void main(String[] args) {
		try{
            /* Cria a classe Veiculo */
            ClassPool pool = ClassPool.getDefault();
            CtClass ctc = pool.makeClass("Veiculo");

            /* Cria atributos */
            CtField ano = new CtField(CtClass.intType, "ano", ctc);
            CtField placa = new CtField(CtClass.voidType, "placa", ctc);

            /* Adiciona os atributos à classe */
            ctc.addField(ano);
            ctc.addField(placa);

            /* Cria métodos */
            CtMethod acelera = new CtMethod(CtClass.voidType, "acelera", null, ctc);
            acelera.setBody("{ System.out.println(\"Acelerando...\"); }");
            CtMethod freia = new CtMethod(CtClass.voidType, "freia", null, ctc);
            freia.setBody("{ System.out.println(\"Freiando...\"); }");

            /* Adiciona os métodos à classe */
            ctc.addMethod(acelera);
            ctc.addMethod(freia);
            
            /* Aqui a classe propriamente dita é criada por meio da conversão do objeto CtClass */
            ctc.writeFile("./");
            
            /* writeFile() havia causado o "congelamento" do objeto, a chamada do método defrost() permite que novas modificações sejam feitas */
            ctc.defrost(); 
            /* Modificando a classe veiculo */
            CtClass v = pool.get("Veiculo");
            
            /* Outra maneira de criar métodos */
            CtMethod getAno = CtMethod.make("public int getAno() {return this.ano;}", v);
            CtMethod getPlaca = CtMethod.make("public String getPlaca() {return this.placa;}", v);
            CtMethod setAno = CtMethod.make("public void setAno (int ano) {this.ano = ano;}", v);
            CtMethod setPlaca = CtMethod.make("public void setPlaca (String placa) {this.placa = placa;}", v);
            /* Adicionando os novos métodos */
            v.addMethod(getAno);
            v.addMethod(getPlaca);
            v.addMethod(setAno);
            v.addMethod(setPlaca);  
            
            /* Modificando o método acelera */
            CtMethod modifica = v.getDeclaredMethod("acelera");
            /* Inserindo novas instruções ao final do método */
            modifica.insertAfter("System.out.println(\"Acelerando mais ainda...\");");
            v.writeFile();
            
            /* Cria outra classe chamada carro */
            CtClass c = pool.makeClass("Carro");
            /* Seta Veiculo como sua superclasse */
            c.setSuperclass(v);
            
            /* Mais do mesmo */
            CtField capacidade = new CtField(CtClass.intType, "capacidade", c);
            c.addField(capacidade);
            CtMethod getCapacidade = CtMethod.make("public int getCapacidade() {return this.capacidade;}", c);
            CtMethod setCapacidade = CtMethod.make("public void setCapacidade (int capacidade) {this.capacidade = capacidade;}", c);
            c.addMethod(getCapacidade);
            c.addMethod(setCapacidade);
            c.writeFile("./");
            
        } 
		catch (Exception e){
            e.printStackTrace();
        }     
	} /* public static void main */
} /* public class t5 */
