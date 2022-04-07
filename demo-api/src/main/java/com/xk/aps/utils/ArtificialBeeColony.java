package com.xk.aps.utils;

/**
 * @author sy
 * @date 2022/3/14 - 12:50
 */
public class ArtificialBeeColony {
    /* ABC算法的控制参数 */
    int NP=20; /* 蜂群大小(雇佣蜂+跟随蜂)*/
    int FoodNumber = NP>>1; /*食物源数量为蜂群数量的一半*/
    int limit = 100;  /*无法通过“限制”的食物源被抛弃*/
    int maxCycle = 1500; /*觅食循环次数*/

    /* 问题的特殊变量*/
    int D = 10; /*待优化问题的参数个数*/
    static double lb = -5.12; /*参数的下界*/
    static double ub = 5.12; /*参数的上界*/

    double Foods[][]=new double[FoodNumber][D];  /*Foods是食物源数组。每一行是一个包含D个待优化参数的向量。矩阵的行数等于食物源数目*/
    double f[]=new double[FoodNumber];           /*f是一个与食物源相关的目标函数值的数组*/
    double fitness[]=new double[FoodNumber];     /*fitness是与食物源相关的质量值的数组*/
    double trial[]=new double[FoodNumber];       /*trial是表明哪个未被改进的食物源的数组*/
    double prob[]=new double[FoodNumber];        /*prob表示食物源被选择的概率的数组*/
    double solution[]=new double[D];             /*解的数组*/

    double ObjValSol;              /*新解的目标函数值*/
    double FitnessSol;             /*新解的适应度值*/
    int neighbour, param2change;   /* 领域搜索公式中param2change对应于新参数, neighbour对应于新食物源*/

    double GlobalMin;                       /*ABC算法求得的最优解*/
    double GlobalParams[]=new double[D];                   /*最优解的参数*/
    double r; /* [0,1)的随机数 */

    /*计算适应度功能*/
    double CalculateFitness(double fun)
    {
        double result=0;
        if(fun >= 0){
            result=1/(fun+1);
        }
        else
        {
            result=1+Math.abs(fun);  /*适应度必须为0-1之间*/
        }
        return result;
    }

    /*记录最优解功能*/
    void MemorizeBestSource()
    {
        int i,j;
        for(i=0;i<FoodNumber;i++)
        {
            if (f[i]<GlobalMin)
            {
                GlobalMin=f[i];
                for(j=0;j<D;j++){
                    GlobalParams[j]=Foods[i][j];}
            }
        }
    }

    /* 初始化函数*/
    void init(int index)
    {
        int j;
        for (j=0;j<D;j++)
        {
            r = ((double)Math.random());
            Foods[index][j]=r*(ub-lb)+lb;
            solution[j]=Foods[index][j];
        }
        f[index]=calculateFunction(solution);
        fitness[index]=CalculateFitness(f[index]);
        trial[index]=0;  /*初始化计数器 */
    }

    /*初始化所有食物源 */
    void initial()
    {
        int i;
        for(i=0;i<FoodNumber;i++)
        {
            init(i);
        }
        GlobalMin=f[0];
        for(i=0;i<D;i++){
            GlobalParams[i]=Foods[0][i];
        }

    }

    /*雇佣蜂阶段*/
    void SendEmployedBees()
    {
        int i,j;
        for (i=0;i<FoodNumber;i++)
        {
            /*随机更改参数*/
            r = ((double)Math.random());
            param2change=(int)(r*D);

            /*使用随机数产生不同的解*/
            r = ((double)Math.random());
            neighbour=(int)(r*FoodNumber);

            for(j=0;j<D;j++)
                solution[j]=Foods[i][j];

            r = ((double)Math.random());
            /*更新搜索公式*/
            solution[param2change]=Foods[i][param2change]+(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;

            /*如果超出边界，则直接等于边界*/
            if (solution[param2change]<lb)
                solution[param2change]=lb;
            if (solution[param2change]>ub)
                solution[param2change]=ub;
            ObjValSol=calculateFunction(solution);  /*计算函数值*/
            FitnessSol=CalculateFitness(ObjValSol); /*计算适应度值*/

            /*贪婪选择一下*/
            if (FitnessSol>fitness[i])
            {

                /*如果变换后的解优于原解，则替换之，并重置计数器*/
                trial[i]=0;
                for(j=0;j<D;j++)
                    Foods[i][j]=solution[j];
                f[i]=ObjValSol;
                fitness[i]=FitnessSol;
            }
            else
            {   /*如果解决方案没有改进，增加它的试验次数*/
                trial[i]=trial[i]+1;
            }

        }

        /*雇佣蜂阶段结束*/

    }

    /*选择食物源的概率与其质量成正比*/
    /*概率值由适应度值计算，并除以最大适应度值来标准化*/
    void CalculateProbabilities()
    {
        int i;
        double maxfit;
        maxfit=fitness[0];
        for (i=1;i<FoodNumber;i++)
        {
            if (fitness[i]>maxfit)
                maxfit=fitness[i];
        }

        for (i=0;i<FoodNumber;i++)
        {
            prob[i]=(0.9*(fitness[i]/maxfit))+0.1;
        }

    }

    /*跟随蜂阶段*/
    void SendOnlookerBees()
    {
        int i,j,t;
        i=0;
        t=0;
        while(t<FoodNumber)
        {
            r = ((double)Math.random());
            if(r<prob[i]) /*根据选择概率选择食物源  采用轮盘赌*/
            {
                t++;
                /*随机更改参数*/
                r = ((double)Math.random());
                param2change=(int)(r*D);

                /*使用随机数产生不同的解*/
                r = ((double)Math.random());
                neighbour=(int)(r*FoodNumber);

                /*确保随机解不等于原解i*/
                while(neighbour == i)
                {
                    r = ((double)Math.random());    /*重新随机*/
                    neighbour=(int)(r*FoodNumber);
                }
                for(j=0;j<D;j++)
                    solution[j]=Foods[i][j];  /*传递参数*/

                r = ((double)Math.random());
                /*邻域更新公式*/
                solution[param2change]=Foods[i][param2change]+(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;

                /*如果超出边界，则直接等于边界*/
                if (solution[param2change]<lb)
                    solution[param2change]=lb;
                if (solution[param2change]>ub)
                    solution[param2change]=ub;
                ObjValSol=calculateFunction(solution);  /*计算函数值*/
                FitnessSol=CalculateFitness(ObjValSol); /*计算适应度值*/

                /*贪婪选择*/
                if (FitnessSol>fitness[i])
                {
                    trial[i]=0;
                    for(j=0;j<D;j++)
                    {
                        Foods[i][j]=solution[j];
                    }
                    f[i]=ObjValSol;
                    fitness[i]=FitnessSol;
                }
                else
                {   /*如果解决方案没有改进，增加它的试验次数*/
                    trial[i]=trial[i]+1;
                }
            }
            i++;
            if (i==FoodNumber-1)
                i=0;
        }
        /*跟随蜂阶段结束 */
    }

    /*确定哪些食物源的试验计数器超过了“limit”值. 在基本的ABC算法中，每次循环只允许有一个侦察蜂*/
    void SendScoutBees()
    {
        int maxtrialindex,i;
        maxtrialindex=0;
        for (i=1;i<FoodNumber;i++)
        {
            if (trial[i]>trial[maxtrialindex])
                maxtrialindex=i;
        }
        if(trial[maxtrialindex]>=limit)
        {
            init(maxtrialindex);
        }
    }

    double calculateFunction(double sol[])
    {
        int st = 1;
        double stop = 0;
        switch (st){
            case 1:
                stop=Rastrigin (sol);
                break;
            case 2:
                stop=sphere (sol);
                break;
            case 3:
                stop=Rosenbrock (sol);
                break;
            case 4:
                stop=Griewank (sol);
                break;
            default:
                break;
        }
        st++;
        return stop;
    }

    /*Rastrigin测试函数*/
    double Rastrigin(double sol[])
    {
        int j;
        double top=0;

        for(j=0;j<D;j++)
        {
            top=top+(Math.pow(sol[j],(double)2)-10*Math.cos(2*Math.PI*sol[j])+10);
        }
        return top;
    }

    /*sphere测试函数*/
    double sphere(double sol[])
    {
        int j;
        double top=0;
        for(j=0;j<D;j++)
        {
            top=top+sol[j]*sol[j];
        }
        return top;
    }

    /*Rosenbrock测试函数*/
    double Rosenbrock(double sol[])
    {
        int j;
        double top=0;
        for(j=0;j<D-1;j++)
        {
            top=top+100*Math.pow((sol[j+1]-Math.pow((sol[j]),(double)2)),(double)2)+Math.pow((sol[j]-1),(double)2);
        }
        return top;
    }

    /*Griewank测试函数*/
    double Griewank(double sol[])
    {
        int j;
        double top1,top2,top;
        top=0;
        top1=0;
        top2=1;
        for(j=0;j<D;j++)
        {
            top1=top1+Math.pow((sol[j]),(double)2);
            top2=top2*Math.cos((((sol[j])/Math.sqrt((double)(j+1)))*Math.PI)/180);

        }
        top=(1/(double)4000)*top1-top2+1;
        return top;
    }

    public static void main(String[] args) {
        ArtificialBeeColony bee = new ArtificialBeeColony();
        int iter = 0;
        int j = 0;
        String[] str1={"Rastrigin","sphere","Rosenbrock","Griewank"};
        double[] str2={5.12,100,30,600};
        /*大循环，采用多个函数测试 */
        for (int i = 0; i < 4; i++) {
            lb = -str2[i];
            ub = str2[i];
            System.out.println("采用" + str1[i] + "测试函数：");
            System.out.println("lb:"+lb+"   ub:"+ub);
            bee.initial();      /*初始化所有食物源 */
            bee.MemorizeBestSource();       /*记录最优解*/
            for (iter = 0; iter < bee.maxCycle; iter++) {
                bee.SendEmployedBees();     /*引领蜂阶段*/
                bee.CalculateProbabilities();       /*计算食物源适应度*/
                bee.SendOnlookerBees();     /*跟随蜂阶段*/
                bee.MemorizeBestSource();       /*记录最优解*/
                bee.SendScoutBees();        /*侦查蜂阶段*/
            }

            for (j = 0; j < bee.D; j++)        /*循环打印10个最优解的参数*/ {
                System.out.println("最优解的参数GlobalParam[" + (j + 1) + "]:" + bee.GlobalParams[j]);
            }
            System.out.println("最优解为:" + bee.GlobalMin + "\n");      /*打印每次运行的最优解*/
        }
    }
}
