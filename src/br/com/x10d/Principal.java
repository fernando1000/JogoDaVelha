package br.com.x10d;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Principal extends Application {
	
	private static final int LARGURA_ALTURA_TELA = 500;
	private static final int TAMANHO_QUADRADO = LARGURA_ALTURA_TELA/5;
	private int quantidadeJogadas = 1;
	private List<PosicaoJogador> posicoesDoJogador1 = new ArrayList<PosicaoJogador>();
	private List<PosicaoJogador> posicoesDoJogador2 = new ArrayList<PosicaoJogador>();
	
	private Text text;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		Group root = new Group();
		Scene scene = new Scene(root, LARGURA_ALTURA_TELA, LARGURA_ALTURA_TELA, Color.AQUAMARINE);
		
		criaBoard(root);
		
		text = new Text();
		//text.setText("Jogada não permitida");
		text.setLayoutX(LARGURA_ALTURA_TELA / 3);
		text.setLayoutY(LARGURA_ALTURA_TELA / 6);
		text.minHeight(40);
		root.getChildren().add(text);
		
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
			text.setStroke(Color.GREEN);
		}
	}
	private void realizaJogada(Button source) {
		quantidadeJogadas++;
		
		PosicaoJogador posicaoJogador = new PosicaoJogador();
		posicaoJogador.posicao = source.getId();
		posicaoJogador.jogador = source.getText();
		
		if(quantidadeJogadas % 2 == 0) {
			source.setText("X");
			posicoesDoJogador1.add(posicaoJogador);
		}else {
			source.setText("O");
			posicoesDoJogador2.add(posicaoJogador);
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
		System.out.println(posicoesDoJogador1);
		if(posicoesDoJogador1.size() < 3) {
			return false;
		}
	
		
		String posicaoAcumulada = "";
		for(PosicaoJogador pj : posicoesDoJogador1) {
			posicaoAcumulada = pj.posicao;
		}
		System.out.println(posicaoAcumulada);
		
		String[] possiveisGanhos = possiveisGanhos();
		for(int i=0; i<possiveisGanhos.length; i++) {
			if(possiveisGanhos[i].equals(posicoesDoJogador1.get(i).posicao)) {
				return true;
			}
		}
		return false;
	}
	
	private String[] possiveisGanhos() {
		String[] ganhos = {"123", "456", "789", "147", "258", "369", "159", "357"};
		return ganhos;
	}
	
}


class PosicaoJogador {
	String posicao;
	String jogador;
}