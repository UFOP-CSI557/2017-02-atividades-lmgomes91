rm(list = ls())


dados <- read.csv2("/home/lucasgomes/MEGAsync/UFOP/Computação Evolucionaria/ES Hibrido/dados.csv",header = F)
colnames(dados) <- c("Replicacao", "Caso","Resultado","Tempo")

dados$Caso[dados$Caso == 3] <- "exec 1"
dados$Caso[dados$Caso == 4] <- "exec 2"
dados$Caso <- as.factor(dados$Caso)
dados$Resultado <- as.numeric(as.character(dados$Resultado))

boxplot(Resultado ~ Caso,data = dados)
boxplot(Tempo ~Caso, data = dados)
# Menor resultado - melhor
dados[dados$Resultado == min(dados$Resultado),] 

#Maior Resultado - pior
dados[dados$Resultado == max(dados$Resultado),] 

# Menor resultado do Caso 3 - melhor
min(dados$Resultado[dados$Caso == "exec 1"])

# Max resultado do Caso 
max(dados$Resultado[dados$Caso == "exec 1"])

# Desvio padrao
sd(dados$Resultado[dados$Caso == "exec 1"])

# Media do Caso 1
mean(dados$Resultado[dados$Caso == "exec 1"])

# Menor resultado do Caso 2 - melhor
min(dados$Resultado[dados$Caso == "exec 2"])

# Max resultado do Caso 2
max(dados$Resultado[dados$Caso == "exec 2"])

# Desvio padrao
sd(dados$Resultado[dados$Caso == "Caso4"])


# Media do Caso 2
mean(dados$Resultado[dados$Caso == "exec 2"])

# Media3 != Media4
t.test(Resultado ~Caso, data = dados)

# Media3 < Media4
t.test(Resultado~Caso, data = dados, alternative = 'l')

# Media3 > Media4
t.test(Resultado~Caso, data = dados, alternative = 'g')

#tempo
t.test(Tempo~Caso, data = dados)

#comparacao do tempo
t.test(Tempo~Caso, data = dados, alternative = 'l')
t.test(Tempo~Caso, data = dados, alternative = 'g')
#TSP

# Caso 3 -> pop - 100, ger - 1000
# Caso 4 -> pop - 1000, ger - 100

