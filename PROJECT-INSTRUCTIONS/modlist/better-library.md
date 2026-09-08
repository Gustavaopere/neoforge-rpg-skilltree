# Better Library

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81c6875ddba45626a03a  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Better Library
- **Arquivo JAR:** `better_lib-neoforge-1.21.1-1.0.111.jar`
- **Versão 1.21.1:** `1.0.111`
- **Categoria:** Biblioteca; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/better-library
- **Função:** Biblioteca utilitária compartilhada para configuração, first-join messages e live messages usadas por mods consumidores.
- **Dependências:** Biblioteca estrutural; necessidade determinada pelos consumidores que declaram Better Library.
- **Compatibilidade/Riscos:** First-join repetido, live-message não-fatal/offline, config client↔server divergente e classloading de UI no dedicated server. Não é substituível automaticamente por outra config library.
- **Sobreposição:** Biblioteca técnica; não tratar como redundante apenas por coexistir com outras libraries.
- **Observações:** Superfícies públicas confirmadas: config, 1st join message e live message. Protocolos/classes internos não foram inferidos sem evidência versionada.
- **Procedência:** Modlist física atual de 07/09/2026 + CurseForge oficial Better Library 1.0.111.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria reconfirmou Better Library 1.0.111 e suas superfícies de config, first-join e live message. A necessidade deve ser determinada pelos consumidores reais; a presença física não foi convertida em decisão de manter/remover.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — config/first-join/live-message authority, lifecycle, side e riscos catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `better_lib-neoforge-1.21.1-1.0.111.jar`, mod id `better_lib`, runtime `1.0.111`. O projeto oficial descreve uma biblioteca simples compartilhada para configuração e mensagens de entrada/live message.

## 1. Papel e autoridade
Better Library é uma **biblioteca utilitária** usada por mods consumidores do mesmo ecossistema. As superfícies publicamente descritas são infraestrutura de configuração e mensagens ao jogador; ela não adiciona progressão, mobs, máquinas ou worldgen próprios.

O mod consumidor continua authority da funcionalidade que usa a biblioteca.

## 2. Configuração
A biblioteca oferece infraestrutura comum de config. Contrato operacional:
- cada consumidor deve ter uma fonte de configuração definida;
- valores server-authoritative não devem ser substituídos por cópia client-side;
- config reload, quando suportado pelo consumidor, precisa invalidar estado derivado;
- defaults da library não devem ser tratados como regra universal para mods que a usam.

## 3. First Join Message
A página oficial cita suporte a **mensagem de primeiro acesso/first join**. Esse tipo de recurso precisa distinguir:
- primeiro acesso real;
- reconnect;
- respawn;
- troca de dimensão;
- entrada em outro servidor/mundo.

Uma mensagem de first join não pode ser usada como authority para conceder reward ou progressão sem lógica explícita do consumidor.

## 4. Live Message
O projeto também cita **live message**, superfície de comunicação/mensagem dinâmica. A ficha não presume formato de transporte, endpoint ou protocolo não documentado. Segurança operacional:
- conteúdo remoto não deve ser tratado como comando de gameplay;
- falha de rede deve ser não-fatal;
- repetição da mensagem não pode multiplicar efeitos no jogo;
- apresentação deve permanecer separada de state persistente.

## 5. Client/server
A página oficial classifica o projeto para Client & Server. Config compartilhada pode existir em ambos os lados; apresentação de mensagem é cliente, enquanto decisão de state do consumidor continua servidor quando aplicável.

Não carregar UI exclusivamente client em bootstrap comum sem dist guard.

## 6. Dependência por consumidores
Better Library não deve ser removida apenas por parecer pequena ou redundante. A necessidade é determinada pelos mods que a declaram como dependência. Outra config library não é substituta binária automática.

## 7. Lifecycle
Validar:
- first login e reconnect;
- multiplayer/server switch;
- config load/reload;
- ausência/indisponibilidade de live message;
- client sem conectividade externa;
- server restart;
- atualização de consumidor mantendo API compatível.

## 8. Riscos
1. First-join message repetida a cada reconnect.
2. Live message causando spam ou bloqueio de login em falha externa.
3. Config client divergindo da server authority.
4. API/library removida enquanto consumidor ainda depende dela.
5. Classloading de UI no dedicated server.
6. Mensagem confundida com sistema de reward/quest.

## 9. Matriz de testes
1. Dedicated server boot com consumidores.
2. Primeiro login vs segundo login.
3. Respawn/dimension change não contam como first join.
4. Falha/offline da live message não impede carregamento.
5. Config default/custom e restart.
6. Coexistência com outros mods de mensagens/UI sem duplicação crítica.

## 10. Evidência
- modlist física atual: Better lib 1.0.111;
- CurseForge oficial Better Library 1.0.111;
- descrição oficial das superfícies config, 1st join message e live message.

> 📚 Exaustividade proporcional: as três superfícies públicas confirmadas — config, first join e live message — estão documentadas com authority/lifecycle/riscos. Nenhum API interno foi inventado.
