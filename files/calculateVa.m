function va = calculateVa(file, v)
    fid = fopen(file,'r');
    N = str2num(fgetl(fid));
    fgetl(fid);
    [num x y vx vy] = textread(file,"%f %f %f %f %f\n",'headerLines', 2);
    
    sumx = 0;
    sumy = 0;

    for i= 1:N
        sumx += vx(i);
        sumy += vy(i);
    end

    vsum = sqrt(sumx^2 + sumy^2);

    va = vsum/(N*v)
 end

