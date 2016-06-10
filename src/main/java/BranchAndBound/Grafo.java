package BranchAndBound;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.Panel;
import javax.swing.JFrame;

import java.util.Stack;

/**
 * @author Mateus
 */
public class Grafo extends JFrame{
	private static final long serialVersionUID = -2707712944901661771L;
        
	Stack <Object> stack = new Stack ();
	mxGraph graph = new mxGraph();
	Object parent = graph.getDefaultParent();
	mxGraphComponent graphComponent;

	mxCell prevVisit;

	private final String STYLE_NORMAL = "NORMAL;fillColor=#61BEF7;strokeColor=#0C2C40";
	private final String STYLE_VISITING = "VISIT;fillColor=#9DD0B6;strokeColor=#206037";
	private final String STYLE_CUT = "CUT;dashed=true;strokeColor=#A61723;fillColor=F2EEAE";

    public Grafo(Nodo raiz){
		try{
			stack.add(graph.insertVertex(parent, raiz.getValor()+"", "", 30, 30, 40, 30, STYLE_NORMAL));
			if(raiz.getFilhos().size() > 0) visitar(raiz, stack.peek());
		}finally{
			graph.getModel().endUpdate();
		}
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setEnabled(false);
		getContentPane().add(graphComponent);
		mxHierarchicalLayout g = new mxHierarchicalLayout(graph);
		g.execute(parent);//run(parent);
                
	}
       
    /**
     *
     * @param n
     * @param o
     */
    public void visitar (Nodo n, Object o){  
        if(n.getFilhos().size() > 0){
			for(Nodo a : n.getFilhos()){
				stack.add(graph.insertVertex(parent, "", a.getValor(), 30, 30, 40, 30, STYLE_NORMAL));
				graph.insertEdge(parent, n.getValor()+"-"+a.getValor(), "", o, stack.peek(), STYLE_NORMAL);
				visitar(a, stack.peek());
			}
		}
	}

    /**
     *
     * @param args
     */
    public static void main(String[] args){
		//Arvore av = Arvore.parse("A()B()E(1)/F()K(2)///C(3)/D()G(4)/H(5)/I(6)/J(7)///");
		/*Grafo frame = new Grafo(av);

		frame.graphComponent.refresh();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);*/


	}
}
