

public class Statistics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
    public double calculateMinValue(double[]array){
        //Return the first value of the array
        return array[0];
    }

    public double calculateMaxValue(double[] array){
        //return the last value of the array
        return array[(array.length -1)];
    }

    public double calculateMean(double[] array){
        //Add all of the values together
        double mean = 0;
        for(int i = 0; i < array.length ; i++){
            mean += array[i];
        }
        //Divide by total number of values
        //return the mean value
        return mean/array.length;
    }

    public double calculateMedian(double[] array){
        double median = 0;
        double arrayIndex = array.length;
        if((array.length%2) == 0){ //if the array contains an even number of elements, divide that number by 2. add together that element and the one before it (since arrays start at 0). Divide this total by 2.
            arrayIndex /= 2;
            median = (array[(int)arrayIndex] + array[(int)(arrayIndex-1)]) / 2;
        }else{ //if the array contains an odd number of elements, divide that number by 2 and round the number down (since arrays start at 0)
            arrayIndex = Math.floor(arrayIndex/2);
            median = array[(int)arrayIndex];
        }

        return median;
    }

    public double calculateMode(double[] array){ //Currently broken
        //loop through the array counting the number of elements that are the same as the element being compared
        //as soon as 1 element is different, we can move onto the next element (efficient)
        //update a variable holding the most common value (if the value appears more than its predecessors)
        int count = 0;
        int maxCount = 0;
        double mode = 0;
        int indexPlaceholder = 0;
        for(int i = 0; i < array.length; i++){
            indexPlaceholder = i;
            count = 0;
            for(int j = indexPlaceholder; j < array.length; j++){
                if(array[j]==array[i]){
                    count++;
                }else{
                    break;
                }
            }

            if(count > maxCount){
                maxCount = count;
                mode = array[i];
            }
        }

        return mode;
    }

}
