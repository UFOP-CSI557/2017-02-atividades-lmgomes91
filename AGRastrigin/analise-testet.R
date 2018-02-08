rm(list = ls())


dados <- read.csv2("/home/lucasgomes/MEGAsync/UFOP/Computação Evolucionaria/AGRastrigin/dados.csv",header = F)
colnames(dados) <- c("Replicacao", "Caso","Resultado","Tempo")

dados$Caso[dados$Caso == 1] <- "Caso1"
dados$Caso[dados$Caso == 2] <- "Caso2"
dados$Caso <- as.factor(dados$Caso)
dados$Resultado <- as.numeric(as.character(dados$Resultado))

boxplot(Resultado ~ Caso,data = dados)
boxplot(Tempo ~Caso, data = dados)
# Menor resultado - melhor
dados[dados$Resultado == min(dados$Resultado),] 

#Maior Resultado - pior
dados[dados$Resultado == max(dados$Resultado),] 

# Max resultado do Caso 1
max(dados$Resultado[dados$Caso == "Caso1"])

# Desvio padrao
sd(dados$Resultado[dados$Caso == "Caso1"])

# Media do Caso 1
mean(dados$Resultado[dados$Caso == "Caso1"])

# Menor resultado do Caso 2 - melhor
min(dados$Resultado[dados$Caso == "Caso2"])

# Max resultado do Caso 2
max(dados$Resultado[dados$Caso == "Caso2"])

# Desvio padrao
sd(dados$Resultado[dados$Caso == "Caso2"])


# Media do Caso 2
mean(dados$Resultado[dados$Caso == "Caso2"])

# Media1 != Media2
t.test(Resultado ~Caso, data = dados)
# Media1 < Media2
t.test(Resultado~Caso, data = dados, alternative = 'l')
# Media1 > Media2
t.test(Resultado~Caso, data = dados, alternative = 'g')
#tempo
t.test(Tempo~Caso, data = dados)
#comparacao do tempo
t.test(Tempo~Caso, data = dados, alternative = 'l')
t.test(Tempo~Caso, data = dados, alternative = 'g')
#TSP

# Caso 1 -> pop - 100, ger - 1000
# Caso 2 -> pop - 1000, ger - 100
