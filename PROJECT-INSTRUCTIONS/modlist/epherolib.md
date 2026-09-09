# EpheroLib — 1.2.0

> **Runtime físico confirmado:** `EpheroLib-1.21.1-NEO-FORGE-1.2.0.jar` · mod id `epherolib` · versão `1.2.0` · NeoForge 1.21.1 · **Client & Server**.

## 1. Papel no modpack
EpheroLib é uma biblioteca de infraestrutura criada para reduzir diferenças entre loaders e oferecer serviços comuns a mods consumidores. Ela não adiciona uma progressão ou sistema de gameplay equivalente a um content mod.

## 2. Cross-loader abstraction
O projeto descreve a biblioteca como uma forma de abstrair código entre Fabric/Forge/NeoForge. O contrato relevante para o pack é a API compartilhada; não assumir que APIs específicas de outro loader existem no runtime NeoForge.

## 3. Configuração comum
A documentação pública informa que EpheroLib inclui **Configurate** para fornecer um sistema comum de config entre plataformas.

O arquivo/schema concreto pertence ao mod consumidor. Não criar config própria da biblioteca por suposição se o consumer não a expõe.

## 4. Networking abstraction
O projeto também fornece uma camada que esconde diferenças de packets/networking entre loaders.

Packets de um consumidor continuam sujeitos a side validation e server authority. A abstraction não transforma client packet em mutation confiável por si só.

## 5. Server-side translations
EpheroLib descreve suporte a traduções server-side baseadas em Gson, permitindo que server-side mods usem localization conforme a linguagem comunicada pelo cliente.

Fallback de idioma precisa ser seguro; uma tradução ausente não deve bloquear execução de gameplay.

## 6. Per-world storage
A documentação menciona uma abstraction de armazenamento Gson para dados persistidos **por mundo**.

Qualquer consumer que use essa camada precisa testar save/reload, migration e corrupção/ausência do arquivo. A biblioteca fornece infraestrutura; o schema e ownership do dado continuam pertencendo ao consumidor.

## 7. Consumers — limite de evidência
O projeto é usado por mods da família Epherical em vários ambientes, mas neste ciclo a modlist física não forneceu evidência suficiente para atribuir com segurança um **consumer top-level específico** da instalação apenas pelo nome dos JARs.

Regra fail-closed: manter a biblioteca enquanto o dependency graph não for auditado no metadata dos consumidores; não declarar Croptopia/ServerBrowser/etc. como ativos sem presença física confirmada.

## 8. Client / Server
O projeto é distribuído para **Client and Server**. O código comum precisa separar corretamente packets/client resources de storage/config server-side.

Dedicated server não pode carregar classes puramente gráficas de um consumer através da abstraction.

## 9. Lifecycle
Validar quando um consumer concreto for identificado:
- mod bootstrap;
- config load/reload;
- packet registration;
- client connect/disconnect;
- world creation/load;
- world save/restart;
- dimension/server shutdown;
- storage migration após update.

## 10. Multiplayer
Networking e server-side translations tornam multiplayer uma superfície real. Cada packet deve ser validado no servidor, e language/preferences do cliente não podem alterar o state de outro jogador indevidamente.

Per-world storage deve manter uma única authority no servidor.

## 11. Riscos
1. remover biblioteca com consumer não mapeado;
2. version mismatch com consumer;
3. config codec/schema drift;
4. packet registration mismatch;
5. packet enviado no side errado;
6. storage Gson incompatível/corrupto;
7. migration ausente entre versões;
8. language fallback falhar;
9. client-only classloading em dedicated server;
10. documentar gameplay fictício para uma biblioteca genérica.

## 12. Matriz de testes
1. Dedicated server boot com EpheroLib 1.2.0.
2. Client connect ao servidor com mesmo mod set.
3. Identificar consumidores via metadata/JAR antes de qualquer remoção.
4. Para cada consumer: config load e restart.
5. Para cada consumer: packet flow principal em dois clientes.
6. Trocar idioma do cliente e validar fallback de mensagens server-side quando usado.
7. Save/restart de qualquer consumer que use per-world storage.
8. Update da biblioteca em cópia de teste com consumers atuais.

**Esta catalogação não afirma que esses testes foram executados.**

## 13. Evidências
- modlist física canônica: JAR/mod id/version/hash;
- GitHub oficial `ExcessiveAmountsOfZombies/EpheroLib`: library cross-loader e dependency model;
- CurseForge/Modrinth oficiais: 1.2.0 NeoForge 1.21.1, Client & Server;
- descrição oficial: Configurate, networking abstraction, server-side translations e Gson per-world storage.

> **Boundary canônico:** EpheroLib possui **infraestrutura compartilhada**. State e gameplay pertencem aos mods consumidores.