package br.com.x10d;

import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Principal extends Application {
	
	private static final int LARGURA_ALTURA_TELA = 500;
	private static final int TAMANHO_QUADRADO = LARGURA_ALTURA_TELA/5;
	private int quantidadeJogadas = 1;
	private Text text;
	private Group root;
	private static final String GANHOU_X = "XXX";
	private static final String GANHOU_O = "OOO";
	private HashMap<String, String> mapa = new HashMap<>();
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		
		TranslateTransition translateTransition = new TranslateTransition();
		translateTransition.setDuration(Duration.seconds(1));
		translateTransition.setToX(350);
		translateTransition.setToY(350);
		translateTransition.setAutoReverse(true);
		translateTransition.setCycleCount(Animation.INDEFINITE);
									Circle circle = new Circle();
									circle.setStroke(Color.RED);
									circle.setFill(Color.BLUE);
									circle.setRadius(15);
									circle.setLayoutX(50);
									circle.setLayoutY(50);
		translateTransition.setNode(circle);
		translateTransition.play();
		
		root = new Group();
		
		Scene scene = new Scene(root, LARGURA_ALTURA_TELA, LARGURA_ALTURA_TELA, Color.AQUAMARINE);
		
		criaBoard(root);
		
		text = new Text();
		//text.setText("Jogada não permitida");
		text.setLayoutX(LARGURA_ALTURA_TELA / 3);
		text.setLayoutY(LARGURA_ALTURA_TELA / 6);
		text.minHeight(40);
		root.getChildren().add(text);
		root.getChildren().add(circle);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private void criaBoard(Group root) {
		for(int i=1; i<=9; i++) {
			Button quadrado = new Button();
			quadrado.setId(""+i);
			quadrado.setMinHeight(TAMANHO_QUADRADO);
			quadrado.setMinWidth(TAMANHO_QUADRADO);
			if(i<=3) {
				quadrado.setLayoutX(i * TAMANHO_QUADRADO);
				quadrado.setLayoutY(TAMANHO_QUADRADO);				
			}
			if(i>=4 && i<=6) {
				quadrado.setLayoutX((i-3) * TAMANHO_QUADRADO);
				quadrado.setLayoutY(TAMANHO_QUADRADO * 2);				
			}
			if(i>=7){
				quadrado.setLayoutX((i-6) * TAMANHO_QUADRADO);
				quadrado.setLayoutY(TAMANHO_QUADRADO * 3);				
			}
			quadrado.setOnMouseClicked(e -> cliqueRelizado(e));
			root.getChildren().add(quadrado);			
		}
	}
	
	private void cliqueRelizado(MouseEvent event) {
		Button source = (Button)event.getSource();
		if(source.getText().isEmpty()) {
			jogoContinua(source);
		}else {
			jogadaNaoPermitida();
		}		
	}
	
	private void jogoContinua(Button source) {
		limpaErro();
		realizaJogada(source);
		
		if(temVencedor()) {
			text.setText("Parabêns");
			text.setFont(new Font(40));
			text.setStroke(Color.GREEN);
		}
	}
	private void realizaJogada(Button botao) {
		quantidadeJogadas++;
		
		if(quantidadeJogadas % 2 == 0) {
			botao.setText("X");
			mapa.put(botao.getId(), botao.getText());
		}else {
			botao.setText("O");
			mapa.put(botao.getId(), botao.getText());
		}
	}
	private void jogadaNaoPermitida() {
		text.setText("Jogada não permitida");
		text.setStroke(Color.RED);
	}
	private void limpaErro() {
		text.setText("");
	}
	
	private boolean temVencedor() {
		String horizontal1 = mapa.get("1")+mapa.get("2")+mapa.get("3");
		String horizontal2 = mapa.get("4")+mapa.get("5")+mapa.get("6");
		String horizontal3 = mapa.get("7")+mapa.get("8")+mapa.get("9");
		String vertical1 = mapa.get("1")+mapa.get("4")+mapa.get("7");
		String vertical2 = mapa.get("2")+mapa.get("5")+mapa.get("8");
		String vertical3 = mapa.get("3")+mapa.get("6")+mapa.get("9");
		String diagonal1 = mapa.get("1")+mapa.get("5")+mapa.get("9");
		String diagonal2 = mapa.get("3")+mapa.get("5")+mapa.get("7");
		
		if(horizontal1.equals(GANHOU_X) || horizontal1.equals(GANHOU_O)) {
			System.out.println("horizontal1: "+horizontal1);
			return true;
		}
		if(horizontal2.equals(GANHOU_X) || horizontal2.equals(GANHOU_O)) {
			System.out.println("horizontal2: "+horizontal2);
			return true;
		}
		if(horizontal3.equals(GANHOU_X) || horizontal3.equals(GANHOU_O)) {
			System.out.println("horizontal3: "+horizontal3);
			return true;
		}
		if(vertical1.equals(GANHOU_X) || vertical1.equals(GANHOU_O)) {
			System.out.println("vertical1: "+vertical1);
			return true;
		}
		if(vertical2.equals(GANHOU_X) || vertical2.equals(GANHOU_O)) {
			System.out.println("vertical2: "+vertical2);
			return true;
		}
		if(vertical3.equals(GANHOU_X) || vertical3.equals(GANHOU_O)) {
			System.out.println("vertical3: "+vertical3);
			return true;
		}
		if(diagonal1.equals(GANHOU_X) || diagonal1.equals(GANHOU_O)) {
			System.out.println("diagonal1: "+diagonal1);
			return true;
		}
		if(diagonal2.equals(GANHOU_X) || diagonal2.equals(GANHOU_O)) {
			System.out.println("diagonal2: "+diagonal2);
			return true;
		}
		return false;
	}

}
