import ml.NeuralNet;


public class VehicleRecognition {
	
	
	
	public double classifyImage(int[] image){
		int size[]={128*128*3,50,1};
		NeuralNet n=new NeuralNet(size);
		n.initializeTheta();
		double o[]=n.calculateOutput(image);
		return o[0];
	}
	
}
