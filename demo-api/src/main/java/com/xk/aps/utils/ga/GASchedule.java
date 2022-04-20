package com.xk.aps.utils.ga;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sy
 * @date 2022/3/16 - 16:17
 */
@Data
@Service
public class GASchedule {
    private int maxMacNum;
    private List<List<double[][]>> job;
    private List<Double> mac;//机器编号
    private int popSize = 100;
    private double[][] pop;
    private ArrayList<Double> bestTime = new ArrayList<>();
    private ArrayList<Double> meanTime;
    private double[][] bestPop;
    private int generation = 0;
    private double[][] fatherPool;
    private double[][] childCross;
    private double[][] childMut;
    private List<List<ArrayList<Double>>> workEnd = new ArrayList<>();

    private int gnmax = 100;//最大代数
    private double pc = 0.8; //交叉概率
    private double pm = 0.2; //变异概率


    public List<Double> createMachine(List<List<double[][]>> scheduleData){
        List<Double> theMac = new ArrayList<>();
        HashSet<Double> hashSet = new HashSet<>();
        for (int i = 0; i < scheduleData.size(); i++) {
            for (int j = 0; j < scheduleData.get(i).size(); j++) {
                for (int k = 0; k < scheduleData.get(i).get(j).length; k++) {
                    double v = scheduleData.get(i).get(j)[k][0];
                    hashSet.add(v);
                }
            }
        }
        maxMacNum = hashSet.size();
        hashSet.forEach((item)->{
            theMac.add(item);
        });
        return theMac;
    }


    public GAResult Schedule(List<List<double[][]>> scheduleData) {

        GAResult result = new GAResult();
        //GATest gaTest = new GATest();
        job = new ArrayList<>();
        job = scheduleData;

        mac = createMachine(scheduleData);

        //初始化种群
        this.pop = InitPop(popSize);
        this.bestPop = new double[gnmax][];

        Lock lock = new ReentrantLock();
        //当前迭代次数小于最大代数
        while(generation < gnmax){
            fatherPool = chooseFather(pop,popSize);
            childCross = crossPox(fatherPool,pc);
            childMut = mutationExchange(childCross,pm);
            ChooseProbResult chooseProbResult = chooseProb(childMut);
            bestPop[generation] = childMut[chooseProbResult.getBestProbCode()];
            pop = childMut;
            generation++;


            //记录这一代最好个体的时间数据
            List<ArrayList<Double>> jobEnd = deCode(childMut[chooseProbResult.getBestProbCode()]).getJobEnd();
            workEnd.add(jobEnd);

        }
        double[] bestOne;
        lock.lock();
        try {
            double min = Double.MAX_VALUE;
            int index = 0;
            for (int i = 0; i < bestTime.size(); i++) {
                if(bestTime.get(i) < min){
                    min = bestTime.get(i);
                    index = i;
                }
            }
            bestOne = bestPop[index];
            result.setBestOne(bestOne);
            result.setWorkEndList(workEnd.get(index));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

        return result;

    }

    private double[][] mutationExchange(double[][] childCross, double pm) {
        int lineLength = 0;
        for (int i = 0; i < childCross.length; i++) {
            lineLength = Math.max(childCross[i].length , lineLength);
        }
        int jobLength = lineLength / 2;

        for (int i = 0; i < childCross.length; i++) {
            double[] child = childCross[i];

            if(Math.random() < pm){

                //得到突变的工序数目
                int jobNum = (int)((Math.random() * jobLength * 0.1) / 1);
                HashSet<Integer> hashSet = new HashSet<>();
                while (hashSet.size() < jobNum) {
                    hashSet.add((int)((Math.random() * jobLength)/1));
                }
                List<Integer> preList = new ArrayList<>();
                List<Integer> list = new ArrayList<>();
                if(hashSet.size() > 0){
                    hashSet.stream().forEach((item)->{
                        list.add(item);
                    });
                }
                preList = list;
                //得到打乱后的工件顺序
                Collections.shuffle(list);
                for (int j = 0; j < list.size(); j++) {
                    child[preList.get(j)] = child[list.get(j)];
                    child[preList.get(j) + jobLength] = child[list.get(j) + jobLength];
                }


                //加工机器突变
                //得到突变机器数量 macMutN
                int macMutN = (int)(((Math.random() * jobLength * 0.1)/1));
                for (int j = 0; j < macMutN; j++){
                    //选择突变的工序位置
                    int jobIndex = (int)((jobLength * Math.random())/1);

                    //找出突变的工件号和工序号
                    //工件号
                    double gene = child[jobIndex];
                    //工序号
                    int checkIndex = checkIndex(child, jobIndex, gene);
                    //查出这个工序有几个可选择的加工设备
                    int jobSize = job.get((int) gene - 1).get(checkIndex - 1).length;
                    //生成一个可选数量之内的数
                    int tmp = (int)((Math.random() * jobSize)/1);
                    //生成新的机器号
                    double newMacCode = job.get((int) gene - 1).get(checkIndex - 1)[tmp][0];
                    child[jobIndex + jobLength] = newMacCode;
                }
                childCross[i] = child;
            }

//            if(Math.random() < pm){
//                //加工机器突变
//                //得到突变机器数量 macMutN
//                int macMutN = (int)(((Math.random() * 0.1 + 0.1) * jobLength)/1);
//                for (int j = 0; j < macMutN; j++){
//                    //选择突变的工序位置
//                    int jobIndex = (int)((jobLength * Math.random())/1);
//
//                    //找出突变的工件号和工序号
//                    //工件号
//                    double gene = child[jobIndex];
//                    //工序号
//                    int checkIndex = checkIndex(child, jobIndex, gene);
//                    //查出这个工序有几个可选择的加工设备
//                    int jobSize = job.get((int) gene - 1).get(checkIndex - 1).length;
//                    //生成一个可选数量之内的数
//                    int tmp = (int)((Math.random() * jobSize)/1);
//                    //生成新的机器号
//                    double newMacCode = job.get((int) gene - 1).get(checkIndex - 1)[tmp][0];
//                    childCross[i][jobIndex + jobLength] = newMacCode;
//                }
//            }
        }
        return childCross;
    }

    private double[][] crossPox(double[][] fatherPool, double pc) {
        int jobN = Integer.MIN_VALUE;
        int lineLength = 0;
        for (int i = 0; i < fatherPool.length; i++) {
            lineLength = Math.max(fatherPool[i].length , lineLength);
        }
        int jobLength = lineLength / 2;
        double[][] child = new double[fatherPool.length][lineLength];

        for (int i = 0; i < jobLength; i++) {
            jobN = Math.max((int)fatherPool[0][i] , jobN);
        }

        for (int i = 0; i < fatherPool.length; i++) {
            double[] parent1 = fatherPool[i];
            double[] parent2 = fatherPool[(int)(Math.random() * fatherPool.length)/1];
            double[] childResult1 = new double[lineLength];
            double[] childResult2 = new double[lineLength];


            if(Math.random() < pc  && jobN>1){
                //job段交叉
                ArrayList<Integer> crossJob = new ArrayList<>();
                while(crossJob.size() == jobN || crossJob.isEmpty()){
                    crossJob.clear();
                    double[] r1 = new double[jobN];
                    for (int j = 0; j < jobN; j++) {
                        r1[j] = Math.random();
                    }
                    for (int j = 0; j < r1.length; j++) {
                        if(r1[j] > 0.5){
                            crossJob.add(j);
                        }
                    }
                }

                int k1 = 0 , k2 = 0;
                for (int j = 0; j < jobLength; j++) {
                    if(crossJob.contains(parent1[j])){
                        childResult1[j] = parent1[j];
                    }else {
                        while(crossJob.contains(parent2[k1])){
                            k1++;
                        }
                        childResult1[j] = parent2[k1];
                        k1++;
                    }
                    if(crossJob.contains(parent2[j])){
                        childResult2[j] = parent2[j];
                    }else {
                        while (crossJob.contains(parent1[k2])){
                            k2++;
                        }
                        childResult2[j] = parent1[k2];
                        k2++;
                    }
                }

                //mac段交叉
                int[] rand = new int[jobLength];
                for (int j = 0; j < jobLength; j++) {
                    rand[j] = (int)(Math.random());
                }
                for (int j = jobLength; j < jobLength * 2; j++) {
                    //调用这个方法找到 childResult1[j - jobLength] 这个基因在childResult1的 [0 , j]上出现了几次
                    //也就是找出这个基因是第几个工件的第几个工序 childResult1[j - jobLength]这个工件的第 index个工序
                    int count1 = checkIndex(childResult1 , j - jobLength, childResult1[j - jobLength]);
                    int count2 = checkIndex(childResult2 , j - jobLength, childResult2[j - jobLength]);
                    if(rand[j - jobLength] == 0){
                        //调用这个方法 找出父类中对应工件的对应的那个工序  得到这个工序的加工机器编号
                        int searchIndex1 = searchIndex(parent1 , childResult1[j - jobLength] , count1);
                        int searchIndex2 = searchIndex(parent2 , childResult2[j - jobLength] , count2);
                        //如果 rand[j - jobLength] = 0 就是用parent1的mac基因  否则使用parent2的基因
                        childResult1[j] = parent1[searchIndex1 + jobLength];
                        childResult2[j] = parent2[searchIndex2 + jobLength];
                    }else {
                        int searchIndex1 = searchIndex(parent2 , childResult1[j - jobLength] , count1);
                        int searchIndex2 = searchIndex(parent1 , childResult2[j - jobLength] , count2);
                        childResult1[j] = parent2[searchIndex1 + jobLength];
                        childResult2[j] = parent1[searchIndex2 + jobLength];
                    }
                }

            }else {
                childResult1 = parent1;
                childResult2 = parent2;
            }
            DecodeResult decodeResult1 = deCode(childResult1);
            DecodeResult decodeResult2 = deCode(childResult2);
            child[i] = decodeResult1.getMaxMacTime() < decodeResult2.getMaxMacTime() ? childResult1 : childResult2;
        }
        return child;
    }


    private double[][] chooseFather(double[][] pop, int popSize) {
        ChooseProbResult chooseProbResult = chooseProb(pop);
        double[][] theFatherPool = new double[popSize][];
        bestTime.add(chooseProbResult.theBestTime);
        float[] p = chooseProbResult.getP();
        double[] prand = new double[popSize];
        for (int i = 0; i < popSize; i++) {
            double r = Math.random();
            for (int j = 0; j < popSize; j++) {
                prand[j] = (double)p[j] - r;
            }
            int cur = 0;
            while(prand[cur] < 0){
                cur++;
            }
            //选中个体的序号
            theFatherPool[i] = pop[cur];
        }

        //精英个体保留
        theFatherPool[(int)((Math.random()*theFatherPool.length)/1)] = pop[chooseProbResult.getBestProbCode()];
        return theFatherPool;
    }

    private ChooseProbResult chooseProb(double[][] pop) {
        ChooseProbResult chooseProbResult = new ChooseProbResult();
        int popScale = pop.length;
        int bestPopCode = 0;
        double[] maxTime = new double[popScale];
        //List<List<ArrayList<Double>>> jonEnd = new ArrayList<>();
        for (int i = 0; i < popScale; i++) {
            //计算适应度
            maxTime[i]=deCode(pop[i]).getMaxMacTime();
            //jonEnd.add(deCode(pop[i]).getJobEnd());
        }
        double theBestTime = Double.MAX_VALUE;
        for (int i = 0; i < maxTime.length; i++) {
            if(maxTime[i] < theBestTime){
                theBestTime = maxTime[i];
                bestPopCode = i;
            }
        }

        //workEnd.add(jonEnd.get(bestPopCode));

        float[] f = new float[maxTime.length];
        for (int i = 0; i < f.length; i++) {
            BigDecimal one = new BigDecimal(1);
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(maxTime[i]));
            f[i] = one.divide(bigDecimal,8, RoundingMode.HALF_UP).floatValue();
        }

        //根据个体的适应度计算其被选择的概率
        BigDecimal fSum = new BigDecimal(0);
        for (int i = 0; i < f.length; i++) {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(f[i]));
            BigDecimal pow = bigDecimal.pow(2);
            fSum = fSum.add(pow);
        }
        float[] ps = new float[popScale];
        for (int i = 0; i < popScale; i++) {
            BigDecimal bigDecimal = new BigDecimal(f[i]);
            BigDecimal pow = bigDecimal.pow(2);
            ps[i] = pow.divide(fSum , 6).floatValue();
        }

        //计算累积概率
        float[] p = new float[popScale];
        p[0] = ps[0];
        for (int i = 1; i < popScale; i++) {
            p[i] = p[i - 1] + ps[i];
        }

        chooseProbResult.setP(p);
        chooseProbResult.setTheBestTime(theBestTime);
        chooseProbResult.setBestProbCode(bestPopCode);
        return chooseProbResult;
    }

    /**
     * 计算适应度
     * @param doubles
     * @return
     */
    private DecodeResult deCode(double[] doubles) {
        DecodeResult decodeResult = new DecodeResult();
        int length = doubles.length / 2;
        double[][] jobSerial = new double[length][];
        for (int i = 0; i < length; i++) {
            double sum = 0;
            //计算 doubles[] 从 0 - i上多少个数与doubles[i]相等
            for (int j = 0; j <= i; j++) {
                if(doubles[j] == doubles[i]){
                    sum++;
                }
            }
            //jobSerial[i] = new double[]{doubles[i], sum};
            jobSerial[i] = new double[]{doubles[i] - 1, sum - 1};
        }
        List<ArrayList<Double>> jobStart = new ArrayList<>(job.size());
        List<ArrayList<Double>> jobEnd = new ArrayList<>(job.size());
        for (int i = 0; i < job.size(); i++) {
            jobStart.add(new ArrayList<Double>());
            jobEnd.add(new ArrayList<Double>());
        }
        List<ArrayList<Double>> macStart = new ArrayList<>(maxMacNum);
        List<ArrayList<Double>> macEnd = new ArrayList<>(maxMacNum);
        for (int i = 0; i < maxMacNum; i++) {
            macStart.add(new ArrayList<Double>());
            macEnd.add(new ArrayList<Double>());
            //macSerial.add(new double[mac_num[i]][]);
        }


        for (int i = 0; i < length; i++) {
            int tmp = job.get((int)(jobSerial[i][0])).get((int)(jobSerial[i][1])).length;
            int index = (int)((Math.random() * tmp)/1);
            double theMacType = job.get((int)(jobSerial[i][0])).get((int)(jobSerial[i][1]))[index][0];
            double jobInMacCode=0;

            int count = -1;
            for (int j = 0; j < length; j++) {
                if((doubles[j] - 1) == (int)(jobSerial[i][0])){
                    count++;
                }
                if(count == (int)(jobSerial[i][1])){
                    jobInMacCode = j + length;
                    break;
                }
            }

            //double theMac = mac.get((int)theMacType - 1).get((int)(doubles[(int)jobInMacCode]) - 1) - 1;
            //获得机器编号
            double theMacCode = doubles[(int)jobInMacCode];

            double theMac = -1;
            for (int z = 0; z < mac.size(); z++) {
                if(mac.get(z) == theMacCode){
                    theMac = z;
                }
            }
            //求出工件开始时间（不是真正的开始时间）
            if(jobSerial[i][1] == 0){
                jobStart.get((int)jobSerial[i][0]).add(0.00);
            }else {
                jobStart.get((int)jobSerial[i][0]).add(jobEnd.get((int)jobSerial[i][0]).get((int)jobSerial[i][1] - 1));

            }
            //将第i个工序插入到机器中加工，如果机器间隔合适，插入间隔中，如不合适，插入到最后一位
            //有一个问题，jobSerial没有调顺序，结构还待优化

            InsertMacResult insertMacResult = new InsertMacResult();

            double[][] tmpDouble = job.get((int) jobSerial[i][0]).get((int)jobSerial[i][1]);
            //找出jobInMacCode机器编号是哪一种机器
            for (int j = 0; j < doubles.length; j++) {

            }
            //找出这种机器在job中的对应位置
            int jobIndex = -1;
            for (int z = 0; z < tmpDouble.length; z++) {
                if(tmpDouble[z][0] == theMacCode){
                    jobIndex = z;
                }
            }

            double jobTime = job.get((int)(jobSerial[i][0])).get((int)(jobSerial[i][1]))[jobIndex][1];
            if(i > 0){
                insertMacResult = insertMac(macStart.get((int)theMac),macEnd.get((int)theMac),jobStart.get((int)jobSerial[i][0]).get((int)jobSerial[i][1]),jobEnd.get((int)jobSerial[i][0]),jobTime);
            }else {
                //TODO 当数组为空时 处理数据
                ArrayList<Double> newMacStart = new ArrayList<>();
                ArrayList<Double> newMacEnd = new ArrayList<>();
                int insertPot = 0;
                double jobEndTime = 0;

                newMacStart.add(0.00);
                newMacEnd.add(jobTime);
                jobEndTime=jobTime;
                insertPot = newMacStart.size() - 1;

                insertMacResult.setJonEndTime(jobEndTime);
                insertMacResult.setInsetPort(insertPot);
                insertMacResult.setMacStartTheMac(newMacStart);
                insertMacResult.setMacEndTheMac(newMacEnd);
            }
            macStart.get((int)theMac).add(insertMacResult.getMacStartTheMac().get(0));
            macEnd.get((int)theMac).add(insertMacResult.getMacEndTheMac().get(0));

            int insetPort = (int)insertMacResult.getInsetPort();
            double jobEndTime = insertMacResult.getJonEndTime();

            jobEnd.get((int)jobSerial[i][0]).add(jobEndTime);

            int point = jobEnd.get((int)jobSerial[i][0]).size() - 1;

            jobStart.get((int)jobSerial[i][0]).set(point,jobEnd.get((int)jobSerial[i][0]).get(point) - jobTime);


        }
        //求出机器最大完工时间
        double maxMacTime = 0;
//        for (int i = 0; i < mac_num.length; i++) {
//            if(!macEnd.get(i).isEmpty()){
//                double max = 0;
//                for (int j = 0; j < macEnd.get(i).size(); j++) {
//                    max = Math.max(max , macEnd.get(i).get(j));
//                }
//                maxMacTime = Math.max(maxMacTime , max);
//            }
//        }
        for (int i = 0; i < jobEnd.size(); i++) {
            for (int j = 0; j < jobEnd.get(i).size(); j++) {
                maxMacTime = Math.max(jobEnd.get(i).get(j) , maxMacTime);
            }
        }
        decodeResult.setMaxMacTime(maxMacTime);
        decodeResult.setJobEnd(jobEnd);
        return decodeResult;
    }

    /**
     * 输入某机器的开始时间和结束时间（一维）
     * 输出间隔时间（一维），从1，2工序的间隔开始，如果没有间隔即为0
     * @param macStart
     * @param macEnd
     * @param theJobStart
     * @param jobEnd
     * @param jobTime
     * @return
     */
    private InsertMacResult insertMac(ArrayList<Double> macStart, ArrayList<Double> macEnd, Double theJobStart, ArrayList<Double> jobEnd, double jobTime) {
        InsertMacResult insertMacResult = new InsertMacResult();
//        List<ArrayList<Double>> macStartTheMac = new ArrayList<>();
//        List<ArrayList<Double>> macEndTheMac = new ArrayList<>();
        ArrayList<Double> newMacStart = new ArrayList<>();
        ArrayList<Double> newMacEnd = new ArrayList<>();
        int insertPot = 0;
        double jobEndTime = 0;
        if(macStart.size() >= 2){
            for (int i = 1; i < macStart.size(); i++) {
                double macInterval=macStart.get(i)-macEnd.get(i-1);
                if(macInterval > jobTime && (Math.max(macEnd.get(i-1),theJobStart) + jobTime) <= macStart.get(i)){
                    double findStart = Math.max(macEnd.get(i-1),theJobStart);

                    for (int j = 0; j < i; j++) {
                        newMacStart.add(macStart.get(j));
                    }
                    newMacStart.add(findStart);
                    for (int j = i; j < macStart.size(); j++) {
                        newMacStart.add(macStart.get(j));
                    }

                    for (int j = 0; j < i; j++) {
                        newMacEnd.add(macEnd.get(j));
                    }
                    newMacEnd.add(findStart + jobTime);
                    for (int j = i; j < macStart.size(); j++) {
                        newMacEnd.add(macEnd.get(j));
                    }

                    jobEndTime = findStart + jobTime;
                    insertPot = i;
//                    macStartTheMac.add(newMacStart);
//                    macEndTheMac.add(newMacEnd);

                    insertMacResult.setJonEndTime(jobEndTime);
                    insertMacResult.setInsetPort(insertPot);
                    insertMacResult.setMacStartTheMac(newMacStart);
                    insertMacResult.setMacEndTheMac(newMacEnd);

                    return insertMacResult;
                }
            }
        }
//        double macMax = 0;
//        for (int i = 0; i < macEnd.size(); i++) {
//            macMax = Math.max(macMax,macEnd.get(i));
//        }
        double macMax = 0;
        for (int i = 0; i < macEnd.size(); i++) {
            macMax = Math.max(macMax,macEnd.get(i));
        }
        newMacStart.add(Math.max(theJobStart,macMax));
        newMacEnd.add(newMacStart.get(newMacStart.size() - 1)+jobTime);
        jobEndTime=newMacStart.get(newMacStart.size() - 1)+jobTime;
        insertPot = newMacStart.size() - 1;

        insertMacResult.setJonEndTime(jobEndTime);
        insertMacResult.setInsetPort(insertPot);
        insertMacResult.setMacStartTheMac(newMacStart);
        insertMacResult.setMacEndTheMac(newMacEnd);
        return insertMacResult;
    }

    /**
     * 初始化种群
     * @param popSize
     * @return
     */
    private double[][] InitPop(int popSize) {
        double[][] myPop = new double[popSize][];

        for (int first = 0; first < popSize; first++) {
            ArrayList<Integer> pool = new ArrayList<>();

            int cur = 1;
            for (int i = 0; i < job.size(); i++) {
                for (int j = 0; j < job.get(i).size(); j++) {
                    pool.add(cur);
                }
                cur++;
            }
            Collections.shuffle(pool);
            ArrayList<Integer> codeMac = new ArrayList<>();
            for (int j = 0; j < pool.size(); j++) {
                //当前生成的是第pool.get(j)个工件的第几个工序的加工机器
                int count = 0;
                for (int i = 0; i < j; i++) {
                    if (pool.get(i).equals(pool.get(j))) {
                        count++;
                    }
                }
                double random = Math.random() * job.get(pool.get(j) - 1).get(count).length;
                int macType = (int)job.get(pool.get(j) - 1).get(count)[(int)(random / 1)][0];
                codeMac.add(macType);
            }

            pool.addAll(codeMac);
            myPop[first] = new double[pool.size()];
            for (int second = 0; second < pool.size(); second++) {
                myPop[first][second] = pool.get(second);
            }
        }
        return myPop;
    }

    /**
     * 找出 childResult1 数组从0 - limit上 target数字出现几次 返回次数
     * @param childResult1
     * @param target
     * @return
     */
    private int checkIndex(double[] childResult1 , int limit, double target) {
        int count = 0;
        for (int i = 0; i <= limit; i++) {
            if(childResult1[i] == target){
                count++;
            }
        }
        return count;
    }

    /**
     * 找出parent1中 v这个数字出现index次的位置
     * @param parent1
     * @param v
     * @param index
     * @return
     */
    private int searchIndex(double[] parent1, double v, int index) {
        for (int i = 0; i < parent1.length/2; i++) {
            if(parent1[i] == v){
                index--;
            }
            if(index == 0){
                return i;
            }
        }
        return -1;
    }

}

@Data
class InsertMacResult{
    public ArrayList<Double> macStartTheMac;
    public ArrayList<Double> macEndTheMac;
    public double jonEndTime;
    public double insetPort;
}

@Data
class ChooseProbResult{
    public float[] p;
    public double theBestTime;
    public int bestProbCode;
}
@Data
class DecodeResult{
    private List<ArrayList<Double>> jobEnd;
    double maxMacTime;
}
